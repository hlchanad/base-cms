package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.form.RoleForm;
import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.response.vo.row.RoleRowVO;
import com.chanhonlun.basecms.service.trait.*;

public interface RoleService extends
        BaseService,
        DefaultServiceHasCRUD<Role, Long>,
        DefaultServiceHasDataTable<Role, Long, RoleRowVO>,
        DefaultServiceHasCreatePage<Role, Long, RoleForm>,
        DefaultServiceHasEditPage<Role, Long, RoleForm>,
        DefaultServiceHasDetailPage<Role, Long> {
}
