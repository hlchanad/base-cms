package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.repository.CmsMenuRepository;
import com.chanhonlun.basecms.req.datatables.CmsMenuListDataTablesInput;
import com.chanhonlun.basecms.service.CmsMenuService;
import com.chanhonlun.basecms.service.DataTablesServiceTrait;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.chanhonlun.basecms.vo.CmsMenuDataTablesVO;
import com.chanhonlun.basecms.vo.CmsMenuTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CmsMenuServiceImpl extends BaseServiceImpl implements CmsMenuService {

    @Autowired
    private CmsMenuDataTablesService cmsMenuDataTablesService;

    @Autowired
    private CmsMenuRepository cmsMenuRepository;

    @Override
    public DataTablesServiceTrait<CmsMenu, Long, CmsMenuTableVO, CmsMenuListDataTablesInput, CmsMenuDataTablesVO> getDataTablesService() {
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
    public CmsMenu findByIdAndIsDeleteFalse(Long id) {
        return cmsMenuRepository.findByIdAndIsDeleteFalse(id);
    }

    @Override
    public CmsMenu softDelete(CmsMenu cmsMenu) {
        cmsMenu.setIsDelete(true);
        cmsMenu.setUpdatedAt(new Date());
        cmsMenu.setUpdatedBy(MyConstants.USER_SYSTEM);
        return cmsMenuRepository.save(cmsMenu);
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
