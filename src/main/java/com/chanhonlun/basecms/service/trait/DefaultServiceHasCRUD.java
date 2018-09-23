package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.constant.Status;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.repository.BaseRepository;

import java.io.Serializable;
import java.util.Date;

public interface DefaultServiceHasCRUD<Pojo extends BasePojo<PK>, PK extends Serializable> {

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

    default Pojo create(Pojo pojo) {
        pojo.setIsDelete(false);
        pojo.setStatus(Status.NORMAL);
        pojo.setCreatedBy(MyConstants.USER_SYSTEM); // TODO replace with Current CMS User later
        pojo.setCreatedAt(new Date());
        pojo.setUpdatedBy(MyConstants.USER_SYSTEM);
        pojo.setUpdatedAt(new Date());
        return getRepository().save(pojo);
    }

    default Pojo update(Pojo pojo) {
        pojo.setUpdatedBy(MyConstants.USER_SYSTEM);
        pojo.setUpdatedAt(new Date());
        return getRepository().save(pojo);
    }
}
