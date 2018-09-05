package com.chanhonlun.basecms.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<Pojo, PK extends Serializable>
        extends CrudRepository<Pojo, PK> {

    Pojo findByIdAndIsDeleteFalse(PK id);
}
