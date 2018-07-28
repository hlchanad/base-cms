package com.chanhonlun.springboottest.pojo;

import com.chanhonlun.springboottest.constant.Status;
import com.chanhonlun.springboottest.constant.SystemParameterDataType;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "SYSTEM_PARAMETER")
public class SystemParameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DEFAULT_ID_GENERATOR")
    @SequenceGenerator(name = "DEFAULT_ID_GENERATOR", sequenceName = "SYS_PARAM_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Column(name = "IS_DELETE")
    private Boolean isDelete;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_AT")
    private Date createdAt;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_AT")
    private Date updatedAt;
}
