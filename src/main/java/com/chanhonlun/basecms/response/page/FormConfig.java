package com.chanhonlun.basecms.response.page;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FormConfig {

    private String id;

    private String action;

    private String method;
}
