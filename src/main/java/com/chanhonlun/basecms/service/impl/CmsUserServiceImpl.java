package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.CmsUserRepository;
import com.chanhonlun.basecms.req.datatables.CmsUserListDataTablesInput;
import com.chanhonlun.basecms.service.CmsUserService;
import com.chanhonlun.basecms.service.DataTablesServiceTrait;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.chanhonlun.basecms.vo.CmsUserDataTablesVO;
import com.chanhonlun.basecms.vo.CmsUserTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmsUserServiceImpl extends BaseServiceImpl implements CmsUserService {

    @Autowired
    private CmsUserDataTablesService cmsUserDataTablesService;

    @Autowired
    private CmsUserRepository cmsUserRepository;

    @Override
    public BaseRepository<CmsUser, Long> getRepository() {
        return cmsUserRepository;
    }

    @Override
    public DataTablesServiceTrait<CmsUser, Long, CmsUserTableVO, CmsUserListDataTablesInput, CmsUserDataTablesVO> getDataTablesService() {
        return cmsUserDataTablesService;
    }

    @Override
    public BreadcrumbUtil getBreadcrumbUtil() {
        return breadcrumbUtil;
    }

    @Override
    public SidebarMenuUtil getSidebarMenuUtil() {
        return sidebarMenuUtil;
    }
}
