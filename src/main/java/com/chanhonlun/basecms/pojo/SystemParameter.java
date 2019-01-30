package com.chanhonlun.basecms.pojo;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.Status;
import com.chanhonlun.basecms.constant.SystemParameterDataType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "SYSTEM_PARAMETER")
@SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "SYS_PARAM_ID_SEQ", allocationSize = 1)
public class SystemParameter extends BasePojo<Long> {

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "KEY")
    private String key;

    @Column(name = "VALUE")
    private String value;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "DATA_TYPE")
    private SystemParameterDataType dataType;

    @IgnoreAutoReflection
    @Column(name = "IS_CONFIGURABLE_IN_CMS")
    private Boolean isConfigurableInCms;

    @IgnoreAutoReflection
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    protected Status status;
}
