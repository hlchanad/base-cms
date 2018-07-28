package com.chanhonlun.springboottest.vo;

import com.chanhonlun.springboottest.pojo.SystemParameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SystemParameterVO {

    private String category;
    private String key;
    private String value;

    public SystemParameterVO(SystemParameter systemParameter) {
        this.category = systemParameter.getCategory();
        this.key      = systemParameter.getKey();
        this.value    = systemParameter.getValue();
    }
}
