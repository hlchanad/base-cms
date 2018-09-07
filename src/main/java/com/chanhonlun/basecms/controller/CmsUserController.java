package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.req.datatables.CmsUserListDataTablesInput;
import com.chanhonlun.basecms.service.CmsUserService;
import com.chanhonlun.basecms.service.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.vo.CmsUserDataTablesVO;
import com.chanhonlun.basecms.vo.CmsUserTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms-user")
public class CmsUserController extends BaseController implements
        DefaultControllerHasDataTable<CmsUser, Long, CmsUserTableVO, CmsUserListDataTablesInput, CmsUserDataTablesVO>,
        DefaultControllerHasDeleteActionButton<CmsUser, Long> {

    @Autowired
    private CmsUserService cmsUserService;

    @Override
    public DefaultServiceHasDataTable<CmsUser, Long, CmsUserTableVO, CmsUserListDataTablesInput, CmsUserDataTablesVO> getDefaultPageHasDataTable() {
        return cmsUserService;
    }

    @Override
    public DefaultServiceHasCRUD<CmsUser, Long> getDefaultPageHasCRUD() {
        return cmsUserService;
    }
}
