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
@Table(name = "CMS_MENU_ROLE")
@SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "CMS_MENU_ROLE_ID_SEQ")
public class CmsMenuRole extends BasePojo<Long> {

    @Column(name = "CMS_MENU_ID")
    private Long cmsMenuId;

    @Column(name = "ROLE_ID")
    private Long roleId;

}
