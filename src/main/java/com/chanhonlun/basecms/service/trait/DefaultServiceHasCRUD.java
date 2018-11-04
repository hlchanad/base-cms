package com.chanhonlun.basecms.service.trait;

import com.chanhonlun.basecms.constant.Status;
import com.chanhonlun.basecms.model.UserPrincipal;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.repository.BaseRepository;

import java.io.Serializable;
import java.util.Date;

public interface DefaultServiceHasCRUD<Pojo extends BasePojo<PK>, PK extends Serializable> {

    BaseRepository<Pojo, PK> getRepository();

    UserPrincipal getCurrentUser();

    default Pojo findByIdAndIsDeleteFalse(PK id) {
        return getRepository().findByIdAndIsDeleteFalse(id);
    }

    default Pojo softDelete(Pojo pojo) {
        CmsUser currentUser = getCurrentUser().getCmsUser();
        pojo.setIsDelete(true);
        pojo.setUpdatedAt(new Date());
        pojo.setUpdatedBy(currentUser.getId());
        return getRepository().save(pojo);
    }

    default Pojo create(Pojo pojo) {
        CmsUser currentUser = getCurrentUser().getCmsUser();
        pojo.setIsDelete(false);
        pojo.setStatus(Status.NORMAL);
        pojo.setCreatedBy(currentUser.getId());
        pojo.setCreatedAt(new Date());
        pojo.setUpdatedBy(currentUser.getId());
        pojo.setUpdatedAt(new Date());
        return getRepository().save(pojo);
    }

    default Pojo update(Pojo pojo) {
        CmsUser currentUser = getCurrentUser().getCmsUser();
        pojo.setUpdatedBy(currentUser.getId());
        pojo.setUpdatedAt(new Date());
        return getRepository().save(pojo);
    }
}
