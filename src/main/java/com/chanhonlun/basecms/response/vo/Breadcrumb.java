package com.chanhonlun.basecms.response.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Breadcrumb {

    private String title;
    private String url;
    @Builder.Default private boolean clickable = true;
}
