package com.chanhonlun.basecms.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostForm extends BaseForm {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;

    private PostDetailForm detailEn;
    private PostDetailForm detailZhHk;

}
