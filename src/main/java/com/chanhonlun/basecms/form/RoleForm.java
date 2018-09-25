package com.chanhonlun.basecms.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoleForm extends BaseForm {

    private String code;

    private String title;

    private String description;
}
