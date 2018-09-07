package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.req.datatables.CmsMenuListDataTablesInput;
import com.chanhonlun.basecms.vo.CmsMenuDataTablesVO;
import com.chanhonlun.basecms.vo.CmsMenuTableVO;

import java.util.List;

public interface CmsMenuService extends
        BaseService,
        DefaultServiceHasCRUD<CmsMenu, Long>,
        DefaultServiceHasDataTable<CmsMenu, Long, CmsMenuTableVO, CmsMenuListDataTablesInput, CmsMenuDataTablesVO> {

    List<CmsMenu> findByParentIdNullAndIsDeleteFalse();

    List<CmsMenu> findByParentIdAndIsDeleteFalse(Long parentId);
}
