package com.chanhonlun.basecms.response.vo;

import com.chanhonlun.basecms.pojo.Image;
import lombok.Data;

@Data
public class ImageCreateResponse {

    private String url;

    public ImageCreateResponse(Image image, String imagePath) {
        this.url = imagePath + image.getFileName();
    }
}
