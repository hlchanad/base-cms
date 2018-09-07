package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.req.datatables.CmsUserListDataTablesInput;
import com.chanhonlun.basecms.vo.CmsUserDataTablesVO;
import com.chanhonlun.basecms.vo.CmsUserTableVO;

public interface CmsUserService extends
        BaseService,
        DefaultServiceHasCRUD<CmsUser, Long>,
        DefaultServiceHasDataTable<CmsUser, Long, CmsUserTableVO, CmsUserListDataTablesInput, CmsUserDataTablesVO> {
}
