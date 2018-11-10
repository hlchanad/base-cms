package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.form.CmsMenuForm;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.response.vo.row.CmsMenuRowVO;
import com.chanhonlun.basecms.service.trait.*;

import java.util.List;

public interface CmsMenuPageService extends
        BasePageService,
        DefaultServiceHasCRUD<CmsMenu, Long>,
        DefaultServiceHasDataTable<CmsMenu, Long, CmsMenuRowVO>,
        DefaultServiceHasCreatePage<CmsMenu, Long, CmsMenuForm>,
        DefaultServiceHasEditPage<CmsMenu, Long, CmsMenuForm>,
        DefaultServiceHasDetailPage<CmsMenu, Long> {

    List<CmsMenu> findByParentIdNullAndIsDeletedFalse();

    List<CmsMenu> findByParentIdAndIsDeletedFalse(Long parentId);
}
