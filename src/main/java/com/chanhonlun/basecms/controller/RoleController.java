package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.*;
import com.chanhonlun.basecms.form.RoleForm;
import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.response.vo.row.RoleRowVO;
import com.chanhonlun.basecms.service.page.BasePageService;
import com.chanhonlun.basecms.service.page.RolePageService;
import com.chanhonlun.basecms.service.trait.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController implements
        DefaultControllerHasDeleteActionButton<Role, Long>,
        DefaultControllerHasDataTable<Role, Long, RoleRowVO>,
        DefaultControllerHasCreatePage<Role, Long, RoleForm>,
        DefaultControllerHasEditPage<Role, Long, RoleForm>,
        DefaultControllerHasDetailPage<Role, Long> {

    @Autowired
    private RolePageService rolePageService;

    @Override
    protected BasePageService getPageService() {
        return rolePageService;
    }

    @Override
    public DefaultServiceHasCreatePage<Role, Long, RoleForm> getDefaultPageHasCreate() {
        return rolePageService;
    }

    @Override
    public DefaultServiceHasDataTable<Role, Long, RoleRowVO> getDefaultPageHasDataTable() {
        return rolePageService;
    }

    @Override
    public DefaultServiceHasCRUD<Role, Long> getDefaultPageHasCRUD() {
        return rolePageService;
    }

    @Override
    public DefaultServiceHasEditPage<Role, Long, RoleForm> getDefaultPageHasEdit() {
        return rolePageService;
    }

    @Override
    public DefaultServiceHasDetailPage<Role, Long> getDefaultPageHasDetail() {
        return rolePageService;
    }
}
