package com.chanhonlun.basecms.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostForm extends BaseForm {

    private Date publishDate;

    private PostDetailForm detailEn;
    private PostDetailForm detailZhHk;

}
