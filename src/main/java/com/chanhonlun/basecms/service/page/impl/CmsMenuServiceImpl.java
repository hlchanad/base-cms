package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.model.component.BaseDataTableConfig;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.CmsMenuRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.service.datatable.impl.CmsMenuDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.CmsMenuService;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.chanhonlun.basecms.vo.row.CmsMenuRowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CmsMenuServiceImpl extends BaseServiceImpl implements CmsMenuService {

    @Autowired
    private CmsMenuDataTableServiceImpl cmsMenuDataTablesService;

    @Autowired
    private CmsMenuRepository cmsMenuRepository;

    @Override
    public BaseRepository<CmsMenu, Long> getRepository() {
        return cmsMenuRepository;
    }

    @Override
    public BaseDataTableService<CmsMenu, Long, CmsMenuRowVO, BaseDataTableInput, BaseDataTableConfig> getDataTablesService() {
        return cmsMenuDataTablesService;
    }

    @Override
    public BreadcrumbUtil getBreadcrumbUtil() {
        return breadcrumbUtil;
    }

    @Override
    public SidebarMenuUtil getSidebarMenuUtil() {
        return sidebarMenuUtil;
    }

    @Override
    public List<CmsMenu> findByParentIdNullAndIsDeleteFalse() {
        return cmsMenuRepository.findByParentIdNullAndIsDeleteFalse(new Sort(Sort.Direction.ASC, "sequence"));
    }

    @Override
    public List<CmsMenu> findByParentIdAndIsDeleteFalse(Long parentId) {
        return cmsMenuRepository.findByParentIdAndIsDeleteFalse(parentId, new Sort(Sort.Direction.ASC, "sequence"));
    }

}
