package com.chanhonlun.basecms.pojo;

import com.chanhonlun.basecms.constant.Status;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CMS_MENU")
public class CmsMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CMS_MENU_ID_GENERATOR")
    @SequenceGenerator(name = "CMS_MENU_ID_GENERATOR", sequenceName = "CMS_MENU_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "IS_DELETE")
    private Boolean isDelete;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_AT")
    private java.util.Date createdAt;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_AT")
    private java.util.Date updatedAt;

}
