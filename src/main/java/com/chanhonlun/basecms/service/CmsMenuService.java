package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.model.BaseListConfig;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.req.datatables.CmsMenuListDataTablesInput;
import com.chanhonlun.basecms.vo.CmsMenuTableVO;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.util.List;

public interface CmsMenuService extends BaseService {

    DataTablesOutput<CmsMenuTableVO> cmsMenuDataTablesAPI(CmsMenuListDataTablesInput input);

    BaseListConfig getListConfig();

    CmsMenu findByIdAndIsDeleteFalse(Long id);

    CmsMenu softDelete(CmsMenu cmsMenu);

    List<CmsMenu> findByParentIdNullAndIsDeleteFalse();

    List<CmsMenu> findByParentIdAndIsDeleteFalse(Long parentId);
}
