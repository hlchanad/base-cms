package com.chanhonlun.basecms.pojo;

import com.chanhonlun.basecms.constant.Status;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class BasePojo<PK extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    protected PK id;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    protected Status status;

    @Column(name = "IS_DELETED")
    protected Boolean isDeleted;

    @Column(name = "CREATED_AT")
    protected Date createdAt;

    @Column(name = "CREATED_BY")
    protected Long createdBy;

    @Column(name = "UPDATED_AT")
    protected Date updatedAt;

    @Column(name = "UPDATED_BY")
    protected Long updatedBy;

}
