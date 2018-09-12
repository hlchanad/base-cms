package com.chanhonlun.basecms.pojo;

import com.chanhonlun.basecms.constant.Language;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class BaseDetailPojo <PK extends Serializable, FK extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_GENERATOR")
    @Column(name = "ID")
    protected PK id;

    @Column(name = "REF_ID")
    protected FK refId;

    @Enumerated(EnumType.STRING)
    @Column(name = "LANG")
    protected Language lang;

}
