package com.chanhonlun.basecms.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CMS_MENU")
@SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "CMS_MENU_ID_SEQ", allocationSize = 1)
public class CmsMenu extends BasePojo<Long> {

    @Column(name = "PARENT_ID")
    private Long parentId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PARENT_ID", insertable = false, updatable = false)
    private CmsMenu parent;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "URL")
    private String url;

    @Column(name = "ICON")
    private String icon;

    @Column(name = "SEQUENCE")
    private Integer sequence;

}
