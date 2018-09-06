package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.repository.BaseRepository;

import java.io.Serializable;
import java.util.Date;

public interface DefaultPageHasCRUD<Pojo extends BasePojo<PK>, PK extends Serializable> {

    BaseRepository<Pojo, PK> getRepository();

    default Pojo findByIdAndIsDeleteFalse(PK id) {
        return getRepository().findByIdAndIsDeleteFalse(id);
    }

    default Pojo softDelete(Pojo pojo) {
        pojo.setIsDelete(true);
        pojo.setUpdatedAt(new Date());
        pojo.setUpdatedBy(MyConstants.USER_SYSTEM);
        return getRepository().save(pojo);
    }
}
