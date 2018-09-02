package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.model.BaseListConfig;
import com.chanhonlun.basecms.model.Breadcrumb;
import com.chanhonlun.basecms.model.DefaultListConfig;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.repository.CmsMenuRepository;
import com.chanhonlun.basecms.req.datatables.CmsMenuListDataTablesInput;
import com.chanhonlun.basecms.service.CmsMenuService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.vo.CmsMenuTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

@Service
public class CmsMenuServiceImpl extends BaseServiceImpl implements CmsMenuService {

    @Autowired
    private CmsMenuDataTablesService cmsMenuDataTablesService;

    @Autowired
    private BreadcrumbUtil breadcrumbUtil;

    @Autowired
    private CmsMenuRepository cmsMenuRepository;

    @Override
    public DataTablesOutput<CmsMenuTableVO> cmsMenuDataTablesAPI(CmsMenuListDataTablesInput input) {
        return cmsMenuDataTablesService.getDataTablesData(input);
    }

    @Override
    public BaseListConfig getListConfig() {
        return DefaultListConfig.builder()
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs(Arrays.asList(
                        Breadcrumb.builder()
                                .title("CMS Menu")
                                .url("/cms-menu")
                                .build()
                )))
                .datatable(cmsMenuDataTablesService.getDataTablesConfig(new HashMap<>()))
                .build();
    }

    @Override
    public CmsMenu findByIdAndIsDeleteFalse(Long id) {
        return cmsMenuRepository.findByIdAndIsDeleteFalse(id);
    }

    @Override
    public CmsMenu softDelete(CmsMenu cmsMenu) {
        cmsMenu.setUpdatedAt(new Date());
        cmsMenu.setUpdatedBy(MyConstants.USER_SYSTEM);
        cmsMenu.setIsDelete(true);
        return cmsMenuRepository.save(cmsMenu);
    }
}
