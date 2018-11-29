package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.constant.ApiResponseCode;
import com.chanhonlun.basecms.model.ApiResponse;
import com.chanhonlun.basecms.pojo.Image;
import com.chanhonlun.basecms.request.ImageCreateRequest;
import com.chanhonlun.basecms.request.ImageListRequest;
import com.chanhonlun.basecms.response.hateoas.ImagesHateoasVO;
import com.chanhonlun.basecms.response.vo.ImageCreateResponse;
import com.chanhonlun.basecms.response.vo.ImageVO;
import com.chanhonlun.basecms.service.ImageService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/image")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Value("${com.chanhonlun.url.domain}")
    private String domain;

    @Value("${com.chanhonlun.url.image}")
    private String imagePath;

    @Autowired
    private ImageService imageService;

    @PostMapping()
    public ResponseEntity<ApiResponse> create(ImageCreateRequest request) {

        if (request.getImage().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Image image = imageService.create(request);

        ImageCreateResponse response = new ImageCreateResponse(new ImageVO(image, imagePath));
        ApiResponse apiResponse = new ApiResponse(ApiResponseCode.STATUS_201_000_SUCCESS, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> list(ImageListRequest request) {

        ImagesHateoasVO imagesHateoasVO = imageService.listWithHateoas(request, "image");

        ApiResponse apiResponse = new ApiResponse(ApiResponseCode.STATUS_200_000_SUCCESS, imagesHateoasVO);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {

        Image image = imageService.findByFileNameAndIsDeletedFalse(fileName);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        InputStream inputStream = imageService.getInputStream(image);

        byte[] media;
        try {
            media = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            logger.error("fail to get byte[] from inputStream, e: {}", e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(media, headers, HttpStatus.OK);

    }

}
