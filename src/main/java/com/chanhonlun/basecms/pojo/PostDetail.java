package com.chanhonlun.basecms.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "POST_DETAIL")
@SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "POST_DTL_ID_SEQ", allocationSize = 1)
public class PostDetail extends BaseDetailPojo<Long, Long> {

    @Column(name = "TITLE")
    private String title;

    @Column(name = "BRIEF")
    private String brief;

    @Column(name = "CONTENT")
    private String content;
}
