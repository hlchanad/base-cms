package com.chanhonlun.basecms.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CMS_USER")
@SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "CMS_USER_ID_SEQ", allocationSize = 1)
public class CmsUser extends BasePojo<Long> {

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL")
    private String email;

}
