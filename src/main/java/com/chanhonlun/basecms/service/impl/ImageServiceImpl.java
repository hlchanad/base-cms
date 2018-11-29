package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.constant.ImageType;
import com.chanhonlun.basecms.format.UriFormat;
import com.chanhonlun.basecms.pojo.Image;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.ImageRepository;
import com.chanhonlun.basecms.request.ImageCreateRequest;
import com.chanhonlun.basecms.request.Paging;
import com.chanhonlun.basecms.response.hateoas.HateoasLink;
import com.chanhonlun.basecms.response.hateoas.ImagesHateoasVO;
import com.chanhonlun.basecms.response.vo.ImageVO;
import com.chanhonlun.basecms.service.ImageService;
import com.chanhonlun.basecms.service.data.impl.BaseServiceImpl;
import com.chanhonlun.basecms.util.PagingUtil;
import com.chanhonlun.basecms.util.StorageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageServiceImpl extends BaseServiceImpl implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Value("${com.chanhonlun.url.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${com.chanhonlun.url.image}")
    private String imagePath;

    @Value("${com.chanhonlun.path.upload.image}")
    private String uploadImagePath;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private StorageUtil storageUtil;

    @Override
    public BaseRepository<Image, Long> getRepository() {
        return imageRepository;
    }

    @Override
    public Image create(ImageCreateRequest request) {

        ImageType imageType = getImageType(request.getImage().getContentType());

        String fileName = getFileName();
        Dimension dimension = getImageDimension(request.getImage());

        boolean saveResult = storageUtil.saveObject(request.getImage(), uploadImagePath, fileName + "." + imageType.getExtension());

        if (!saveResult) return null;

        Image image = new Image();
        image.setImageType(imageType);
        image.setFileName(fileName);
        image.setOriginalFileName(request.getImage().getOriginalFilename());
        image.setFileSize(request.getImage().getSize());
        image.setWidth(Optional.ofNullable(dimension).map(Dimension::getWidth).orElse(0d));
        image.setHeight(Optional.ofNullable(dimension).map(Dimension::getHeight).orElse(0d));
        image = create(image);

        return image;
    }

    @Override
    public Page<Image> list(Paging paging) {
        return imageRepository.findByIsDeletedFalse(PagingUtil.parsePagination(paging, Sort.Direction.ASC, "id"));
    }

    @Override
    public ImagesHateoasVO listWithHateoas(Paging paging, String section) {

        if (paging.getPage() != null) {
            paging.setPage(paging.getPage() - 1); // support pagination.js
        }

        Pageable pageable = PagingUtil.parsePagination(paging, Sort.Direction.ASC, "id");

        Page<Image> images = imageRepository.findByIsDeletedFalse(pageable);

        List<ImageVO> imageVOs = images
                .stream()
                .map(image -> new ImageVO(image, imagePath))
                .collect(Collectors.toList());

        Map<String, Pageable> linkMap = PagingUtil.getHateoasPageable(images, pageable);

        Map<String, HateoasLink> links = linkMap.entrySet().stream()
                .map(entry -> {
                    if (entry.getValue() == null) {
                        return new ImmutablePair<>(entry.getKey(), new HateoasLink(entry.getKey(), null));
                    }

                    Paging paging1 = PagingUtil.getPaging(entry.getValue());

                    String query = "?" + new ObjectMapper().convertValue(paging1, UriFormat.class);
                    HateoasLink hateoasLink = new HateoasLink(entry.getKey(), domain + contextPath + "/" + section + query);

                    return new ImmutablePair<>(entry.getKey(), hateoasLink);
                })
                .collect(Collectors.toMap(ImmutablePair::getKey, ImmutablePair::getValue));

        return new ImagesHateoasVO(imageVOs, links, images.getTotalElements());
    }

    @Override
    public InputStream getInputStream(Image image) {
        return storageUtil.getObject(uploadImagePath, image.getFileName() + "." + image.getImageType().getExtension());
    }

    @Override
    public Image findByFileNameAndIsDeletedFalse(String fileName) {
        return imageRepository.findByFileNameAndIsDeletedFalse(fileName);
    }

    private ImageType getImageType(@Nullable String contentType) {

        if (contentType == null) {
            throw new IllegalArgumentException("content type is null");
        }

        String[] contentTypeSplit = contentType.split("/");

        if (!"image".equalsIgnoreCase(contentTypeSplit[0])) {
            throw new IllegalArgumentException("only image type is supported");
        }

        ImageType imageType = ImageType.valueOfIgnoreCase(contentTypeSplit[1]);

        if (imageType == null) {
            throw new IllegalArgumentException(contentTypeSplit[1] + " is not supported for image");
        }

        return imageType;
    }

    private String getFileName() {
        String fileName = ObjectId.get().toString();
        Image image = imageRepository.findByFileNameAndIsDeletedFalse(fileName);
        return (image != null) ? getFileName() : fileName;
    }

    private Dimension getImageDimension(MultipartFile image) {

        Dimension dimension;

        try (ImageInputStream inputStream = ImageIO.createImageInputStream(image.getInputStream())) {

            final Iterator<ImageReader> readers = ImageIO.getImageReaders(inputStream);

            if (!readers.hasNext()) {
                throw new IllegalArgumentException("no image is found");
            }

            ImageReader reader = readers.next();
            reader.setInput(inputStream);
            dimension = new Dimension(reader.getWidth(0), reader.getHeight(0));

            reader.dispose();

        } catch (IOException e) {
            logger.error("fail getting image dimension, e: {}", e);
            return null;
        }

        return dimension;
    }

}
