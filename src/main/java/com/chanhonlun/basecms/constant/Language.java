package com.chanhonlun.basecms.constant;

public enum Language {

    EN ("English"),

    ZH_HK ("繁體中文"),

    ;

    private String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
