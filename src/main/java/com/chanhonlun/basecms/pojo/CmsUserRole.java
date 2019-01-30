package com.chanhonlun.basecms.pojo;

import com.chanhonlun.basecms.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CMS_USER_ROLE")
@SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "CMS_USER_ROLE_ID_SEQ", allocationSize = 1)
public class CmsUserRole extends BasePojo<Long> {

    @Column(name = "CMS_USER_ID")
    private Long cmsUserId;

    @Column(name = "ROLE_ID")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    protected Status status;
}
