package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.pojo.BaseDetailPojo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseDetailRepository<DetailPojo extends BaseDetailPojo<PK, FK>, PK extends Serializable, FK extends Serializable>
        extends CrudRepository<DetailPojo, PK> {

    DetailPojo findByRefIdAndLang(FK refId, Language language);

}
