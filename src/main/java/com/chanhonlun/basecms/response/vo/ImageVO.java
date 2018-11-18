package com.chanhonlun.basecms.response.vo;

import com.chanhonlun.basecms.pojo.Image;
import lombok.Data;

@Data
public class ImageVO {

    private String url;

    public ImageVO(Image image, String imagePath) {
        this.url = imagePath + image.getFileName();
    }
}
