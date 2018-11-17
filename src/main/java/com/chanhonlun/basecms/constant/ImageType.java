package com.chanhonlun.basecms.constant;

public enum ImageType {

    BMP,

    GIF,

    JPG,

    PNG,

    ;

    public static ImageType valueOfIgnoreCase(String type) {
        for (ImageType imageType : values()) {
            if (imageType.name().equalsIgnoreCase(type)) {
                return imageType;
            }
        }
        return null;
    }

    public String getExtension() {
        return this.name().toLowerCase();
    }
}
