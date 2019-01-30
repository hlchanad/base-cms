package com.chanhonlun.basecms.pojo;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

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

    @IgnoreAutoReflection
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CMS_USER_ROLE",
            joinColumns = @JoinColumn(name = "CMS_USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List<Role> roles;

    @IgnoreAutoReflection
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    protected Status status;
}
