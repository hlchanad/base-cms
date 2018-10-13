package com.chanhonlun.basecms.service.data.impl;

import com.chanhonlun.basecms.pojo.CmsUserRole;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.CmsUserRoleRepository;
import com.chanhonlun.basecms.service.data.CmsUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmsUserRoleServiceImpl extends BaseServiceImpl implements CmsUserRoleService {

    @Autowired
    private CmsUserRoleRepository cmsUserRoleRepository;

    @Override
    public BaseRepository<CmsUserRole, Long> getRepository() {
        return cmsUserRoleRepository;
    }
}
