package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.form.CmsUserForm;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.response.vo.row.CmsUserRowVO;
import com.chanhonlun.basecms.service.trait.*;

public interface CmsUserService extends
        BaseService,
        DefaultServiceHasCRUD<CmsUser, Long>,
        DefaultServiceHasDataTable<CmsUser, Long, CmsUserRowVO>,
        DefaultServiceHasCreatePage<CmsUser, Long, CmsUserForm>,
        DefaultServiceHasEditPage<CmsUser, Long, CmsUserForm>,
        DefaultServiceHasDetailPage<CmsUser, Long> {
}
