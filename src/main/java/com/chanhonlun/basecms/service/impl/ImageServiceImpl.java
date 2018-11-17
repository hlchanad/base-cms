package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.constant.ImageType;
import com.chanhonlun.basecms.pojo.Image;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.ImageRepository;
import com.chanhonlun.basecms.request.ImageCreateRequest;
import com.chanhonlun.basecms.service.ImageService;
import com.chanhonlun.basecms.service.data.impl.BaseServiceImpl;
import com.chanhonlun.basecms.util.StorageUtil;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
public class ImageServiceImpl extends BaseServiceImpl implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Value("${com.chanhonlun.path.upload.image}")
    private String imageUploadPath;

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

        String    fileName  = getFileName(imageType);
        String    uri       = imageUploadPath + fileName;
        Dimension dimension = getImageDimension(request.getImage());

        boolean saveResult = storageUtil.saveObject(request.getImage(), uri, fileName);

        if (!saveResult) return null;

        Image image = new Image();
        image.setUri(uri);
        image.setImageType(imageType);
        image.setFileName(fileName);
        image.setFileSize(request.getImage().getSize());
        image.setWidth(Optional.ofNullable(dimension).map(Dimension::getWidth).orElse(0d));
        image.setHeight(Optional.ofNullable(dimension).map(Dimension::getHeight).orElse(0d));
        image = create(image);

        return image;
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

    private String getFileName(ImageType imageType) {
        String fileName = ObjectId.get() + "." + imageType.getExtension();
        Image image = imageRepository.findByFileNameAndIsDeletedFalse(fileName);
        return (image != null) ? getFileName(imageType) : fileName;
    }

    private Dimension getImageDimension(MultipartFile image) {

        Dimension dimension;

        try (ImageInputStream inputStream = ImageIO.createImageInputStream(image.getInputStream())){

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
