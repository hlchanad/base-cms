package com.chanhonlun.basecms.pojo;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "POST")
@SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "POST_ID_SEQ", allocationSize = 1)
public class Post extends BasePojo<Long> {

    @Column(name = "THUMBNAIL")
    private Long thumbnail;

    @Column(name = "PUBLISH_DATE")
    private Date publishDate;

    @IgnoreAutoReflection
    @ManyToOne
    @JoinFormula("(SELECT D.ID FROM BASE_SERVER.POST_DETAIL D WHERE D.REF_ID = ID AND D.LANG = 'EN')")
    private PostDetail detailEn;

    @IgnoreAutoReflection
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    protected Status status;

}
