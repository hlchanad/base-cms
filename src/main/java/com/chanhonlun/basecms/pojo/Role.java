package com.chanhonlun.basecms.pojo;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ROLE")
@SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "ROLE_ID_SEQ", allocationSize = 1)
public class Role extends BasePojo<Long> {

    @Column(name = "CODE")
    private String code;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @IgnoreAutoReflection
    @Column(name = "SELECTABLE")
    private Boolean selectable;

    @IgnoreAutoReflection
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    protected Status status;
}
