package com.chanhonlun.basecms.pojo;

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

    @Column(name = "PUBLISH_DATE")
    private Date publishDate;

    @ManyToOne
    @JoinFormula("(SELECT D.ID FROM BASE_SERVER.POST_DETAIL D WHERE D.REF_ID = ID AND D.LANG = 'EN')")
    private PostDetail detailEn;

}
