package com.chanhonlun.basecms.service.data;

import com.chanhonlun.basecms.pojo.CmsUserRole;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;

import java.util.List;

public interface CmsUserRoleService extends DefaultServiceHasCRUD<CmsUserRole, Long> {

    List<CmsUserRole> findByCmsUserIdAndIsDeletedFalse(Long cmsUserId);
}
