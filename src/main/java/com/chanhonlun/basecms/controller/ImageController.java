package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.constant.ApiResponseCode;
import com.chanhonlun.basecms.model.ApiResponse;
import com.chanhonlun.basecms.pojo.Image;
import com.chanhonlun.basecms.request.ImageCreateRequest;
import com.chanhonlun.basecms.request.ImageListRequest;
import com.chanhonlun.basecms.response.vo.ImageCreateResponse;
import com.chanhonlun.basecms.response.vo.ImageVO;
import com.chanhonlun.basecms.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping()
    public ResponseEntity<ApiResponse> list(ImageListRequest request) {

        List<ImageVO> imageVOs = imageService.list(request.getPaging())
                .stream()
                .map(image -> new ImageVO(image, imagePath))
                .collect(Collectors.toList());

        ApiResponse apiResponse = new ApiResponse(ApiResponseCode.STATUS_200_000_SUCCESS, imageVOs);

        return ResponseEntity.ok(apiResponse);
    }

}
