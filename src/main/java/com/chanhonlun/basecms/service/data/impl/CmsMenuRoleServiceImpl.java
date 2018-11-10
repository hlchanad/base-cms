package com.chanhonlun.basecms.service.data.impl;

import com.chanhonlun.basecms.pojo.CmsMenuRole;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.CmsMenuRoleRepository;
import com.chanhonlun.basecms.service.data.CmsMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsMenuRoleServiceImpl extends BaseServiceImpl implements CmsMenuRoleService {

    @Autowired
    private CmsMenuRoleRepository cmsMenuRoleRepository;

    @Override
    public BaseRepository<CmsMenuRole, Long> getRepository() {
        return cmsMenuRoleRepository;
    }

    @Override
    public List<CmsMenuRole> findByCmsMenuIdAndIsDeleteFalse(Long cmsMenuId) {
        return cmsMenuRoleRepository.findByCmsMenuIdAndIsDeleteFalse(cmsMenuId);
    }
}
