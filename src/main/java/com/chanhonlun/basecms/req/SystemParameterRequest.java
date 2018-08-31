package com.chanhonlun.basecms.req;

import com.chanhonlun.basecms.constant.SystemParameterDataType;
import lombok.Data;

@Data
public class SystemParameterRequest {

    private String category;
    private String key;
    private String value;
    private String description;
    private SystemParameterDataType dataType;
    private Boolean isConfigurableInCms;

}
