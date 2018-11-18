package com.chanhonlun.basecms.response.vo;

import lombok.Data;

@Data
public class ImageCreateResponse {

    private ImageVO image;

    public ImageCreateResponse(ImageVO imageVO) {
        this.image = imageVO;
    }
}
