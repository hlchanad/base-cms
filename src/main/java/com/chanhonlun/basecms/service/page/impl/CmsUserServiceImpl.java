package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.model.component.BaseDataTableConfig;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.CmsUserRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.service.datatable.impl.CmsUserDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.CmsUserService;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.chanhonlun.basecms.vo.row.CmsUserRowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CmsUserServiceImpl extends BaseServiceImpl implements CmsUserService {

    @Autowired
    private CmsUserDataTableServiceImpl cmsUserDataTablesService;

    @Autowired
    private CmsUserRepository cmsUserRepository;

    @Override
    public BaseRepository<CmsUser, Long> getRepository() {
        return cmsUserRepository;
    }

    @Override
    public BaseDataTableService<CmsUser, Long, CmsUserRowVO, BaseDataTableInput, BaseDataTableConfig> getDataTablesService() {
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
