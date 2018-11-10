package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.BasePojo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<Pojo extends BasePojo<PK>, PK extends Serializable>
        extends CrudRepository<Pojo, PK> {

    Pojo findByIdAndIsDeletedFalse(PK id);
}
