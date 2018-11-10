package com.chanhonlun.basecms.service.data;

import com.chanhonlun.basecms.pojo.CmsMenuRole;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;

import java.util.List;

public interface CmsMenuRoleService extends DefaultServiceHasCRUD<CmsMenuRole, Long> {

    List<CmsMenuRole> findByCmsMenuIdAndIsDeleteFalse(Long cmsMenuId);
}
