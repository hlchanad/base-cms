package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.req.datatables.CmsMenuListDataTablesInput;
import com.chanhonlun.basecms.vo.CmsMenuDataTablesVO;
import com.chanhonlun.basecms.vo.CmsMenuTableVO;

import java.util.List;

public interface CmsMenuService extends
        BaseService,
//        DefaultPageHasCRUD<CmsMenu, Long>,
        DefaultPageHasDataTable<CmsMenu, Long, CmsMenuTableVO, CmsMenuListDataTablesInput, CmsMenuDataTablesVO> {

    CmsMenu findByIdAndIsDeleteFalse(Long id);

    CmsMenu softDelete(CmsMenu cmsMenu);

    List<CmsMenu> findByParentIdNullAndIsDeleteFalse();

    List<CmsMenu> findByParentIdAndIsDeleteFalse(Long parentId);
}
