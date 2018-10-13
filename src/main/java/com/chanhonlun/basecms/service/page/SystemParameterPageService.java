package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.form.SystemParameterForm;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.response.vo.row.SystemParameterRowVO;
import com.chanhonlun.basecms.service.trait.*;

public interface SystemParameterPageService extends
        BasePageService,
        DefaultServiceHasCRUD<SystemParameter, Long>,
        DefaultServiceHasDataTable<SystemParameter, Long, SystemParameterRowVO>,
        DefaultServiceHasCreatePage<SystemParameter, Long, SystemParameterForm>,
        DefaultServiceHasEditPage<SystemParameter, Long, SystemParameterForm>,
        DefaultServiceHasDetailPage<SystemParameter, Long> {
}