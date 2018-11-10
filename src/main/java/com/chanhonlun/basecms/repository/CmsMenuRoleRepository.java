package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.CmsMenuRole;

import java.util.List;

public interface CmsMenuRoleRepository extends BaseRepository<CmsMenuRole, Long> {

    List<CmsMenuRole> findByCmsMenuIdAndIsDeleteFalse(Long cmsMenuId);
}
