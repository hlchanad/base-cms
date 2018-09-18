package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDataTable;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDeleteActionButton;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.service.page.BaseService;
import com.chanhonlun.basecms.service.page.CmsUserService;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.response.vo.row.CmsUserRowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms-user")
public class CmsUserController extends BaseController implements
        DefaultControllerHasDataTable<CmsUser, Long, CmsUserRowVO>,
        DefaultControllerHasDeleteActionButton<CmsUser, Long> {

    @Autowired
    private CmsUserService cmsUserService;

    @Override
    public DefaultServiceHasDataTable<CmsUser, Long, CmsUserRowVO> getDefaultPageHasDataTable() {
        return cmsUserService;
    }

    @Override
    public DefaultServiceHasCRUD<CmsUser, Long> getDefaultPageHasCRUD() {
        return cmsUserService;
    }

    @Override
    protected BaseService getService() {
        return cmsUserService;
    }
}
