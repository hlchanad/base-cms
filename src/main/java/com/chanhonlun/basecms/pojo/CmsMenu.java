package com.chanhonlun.basecms.pojo;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CMS_MENU")
@SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "CMS_MENU_ID_SEQ", allocationSize = 1)
public class CmsMenu extends BasePojo<Long> {

    @Column(name = "PARENT_ID")
    private Long parentId;

    @IgnoreAutoReflection
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

    @IgnoreAutoReflection
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CMS_MENU_ROLE",
            joinColumns = @JoinColumn(name = "CMS_MENU_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles;

}
