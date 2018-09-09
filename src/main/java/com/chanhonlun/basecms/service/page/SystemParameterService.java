package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.vo.row.SystemParameterRowVO;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;

public interface SystemParameterService extends
        BaseService,
        DefaultServiceHasCRUD<SystemParameter, Long>,
        DefaultServiceHasDataTable<SystemParameter, Long, SystemParameterRowVO> {

    BaseCreatePageConfig getCreatePageConfig();
}