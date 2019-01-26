package com.chanhonlun.basecms.form;

import com.chanhonlun.basecms.util.DateUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class PostForm extends BaseForm {

    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_2)
    private Date publishDate;

    @DateTimeFormat(pattern = "HH:mm")
    private Date time;

    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_1)
    private Date date;

    @DateTimeFormat(pattern = DateUtil.DATE_FORMAT_2)
    private Date dateTime;

    private PostDetailForm detailEn;
    private PostDetailForm detailZhHk;

}
