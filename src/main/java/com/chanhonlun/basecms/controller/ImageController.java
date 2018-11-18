package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.constant.ApiResponseCode;
import com.chanhonlun.basecms.model.ApiResponse;
import com.chanhonlun.basecms.pojo.Image;
import com.chanhonlun.basecms.request.ImageCreateRequest;
import com.chanhonlun.basecms.response.vo.ImageCreateResponse;
import com.chanhonlun.basecms.response.vo.ImageVO;
import com.chanhonlun.basecms.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

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

}
