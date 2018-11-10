package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.CmsUserRole;

import java.util.List;

public interface CmsUserRoleRepository extends BaseRepository<CmsUserRole, Long> {

    List<CmsUserRole> findByCmsUserIdAndIsDeletedFalse(Long cmsUserId);
}
