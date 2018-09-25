package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.*;
import com.chanhonlun.basecms.form.RoleForm;
import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.response.vo.row.RoleRowVO;
import com.chanhonlun.basecms.service.page.BaseService;
import com.chanhonlun.basecms.service.page.RoleService;
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
    private RoleService roleService;

    @Override
    protected BaseService getService() {
        return roleService;
    }

    @Override
    public DefaultServiceHasCreatePage<Role, Long, RoleForm> getDefaultPageHasCreate() {
        return roleService;
    }

    @Override
    public DefaultServiceHasDataTable<Role, Long, RoleRowVO> getDefaultPageHasDataTable() {
        return roleService;
    }

    @Override
    public DefaultServiceHasCRUD<Role, Long> getDefaultPageHasCRUD() {
        return roleService;
    }

    @Override
    public DefaultServiceHasEditPage<Role, Long, RoleForm> getDefaultPageHasEdit() {
        return roleService;
    }

    @Override
    public DefaultServiceHasDetailPage<Role, Long> getDefaultPageHasDetail() {
        return roleService;
    }
}
