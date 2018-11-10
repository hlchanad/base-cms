package com.chanhonlun.basecms.response.page;

import lombok.Data;

@Data
public abstract class BaseConfig {

    private String pageTitle;

    public BaseConfig(String pageTitle) {
        this.pageTitle = pageTitle;
    }
}
