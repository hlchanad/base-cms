package com.chanhonlun.basecms.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CmsMenuForm extends BaseForm {

    private Long parentId;

    private String title;

    private String url;

    private String icon;

    private Integer sequence;

    private List<Long> roles;
}
