package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.vo.row.CmsUserRowVO;

public interface CmsUserService extends
        BaseService,
        DefaultServiceHasCRUD<CmsUser, Long>,
        DefaultServiceHasDataTable<CmsUser, Long, CmsUserRowVO> {
}
