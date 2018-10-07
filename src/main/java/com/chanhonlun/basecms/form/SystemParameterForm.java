package com.chanhonlun.basecms.form;

import com.chanhonlun.basecms.constant.SystemParameterDataType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SystemParameterForm extends BaseForm {

    private String category;

    private String key;

    private String value;

    private String description;

    private SystemParameterDataType dataType;
}
