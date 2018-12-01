package com.chanhonlun.basecms.response.vo;

import com.chanhonlun.basecms.pojo.Image;
import lombok.Data;

@Data
public class ImageVO {

    private String fileName;

    private String originalFileName;

    private String url;

    private Double width;

    private Double height;

    private Long fileSize;

    public ImageVO(Image image, String imagePath) {
        this.fileName = image.getFileName();
        this.originalFileName = image.getOriginalFileName();
        this.url = imagePath + image.getFileName();
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.fileSize = image.getFileSize();
    }
}
