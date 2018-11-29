package com.chanhonlun.basecms.pojo;

import com.chanhonlun.basecms.constant.ImageType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "IMAGE")
@SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "IMAGE_ID_SEQ")
public class Image extends BasePojo<Long>{

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

    @Enumerated(EnumType.STRING)
    @Column(name = "IMAGE_TYPE")
    private ImageType imageType;

    @Column(name = "WIDTH")
    private Double width;

    @Column(name = "HEIGHT")
    private Double height;

    @Column(name = "FILE_SIZE")
    private Long fileSize;
}
