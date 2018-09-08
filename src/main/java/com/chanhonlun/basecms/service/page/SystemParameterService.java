package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.vo.row.SystemParameterRowVO;

public interface SystemParameterService extends
        BaseService,
        DefaultServiceHasCRUD<SystemParameter, Long>,
        DefaultServiceHasDataTable<SystemParameter, Long, SystemParameterRowVO> {

}