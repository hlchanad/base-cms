package com.chanhonlun.basecms.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageCreateRequest {

    private MultipartFile image;
}
