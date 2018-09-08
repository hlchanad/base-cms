package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.response.vo.row.CmsMenuRowVO;

import java.util.List;

public interface CmsMenuService extends
        BaseService,
        DefaultServiceHasCRUD<CmsMenu, Long>,
        DefaultServiceHasDataTable<CmsMenu, Long, CmsMenuRowVO> {

    List<CmsMenu> findByParentIdNullAndIsDeleteFalse();

    List<CmsMenu> findByParentIdAndIsDeleteFalse(Long parentId);
}
