package com.chanhonlun.basecms.response.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FieldOption {

    private String id;
    private String title;
    private String value;

}
