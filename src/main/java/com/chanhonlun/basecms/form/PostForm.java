package com.chanhonlun.basecms.form;

import lombok.Data;

import java.util.Date;

@Data
public class PostForm {

    private Date publishDate;

    private PostDetailForm detailEn;
    private PostDetailForm detailZhHk;

}
