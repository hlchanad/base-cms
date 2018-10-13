package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.*;
import com.chanhonlun.basecms.form.CmsUserForm;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.response.vo.row.CmsUserRowVO;
import com.chanhonlun.basecms.service.page.BasePageService;
import com.chanhonlun.basecms.service.page.CmsUserPageService;
import com.chanhonlun.basecms.service.trait.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms-user")
public class CmsUserController extends BaseController implements
        DefaultControllerHasDataTable<CmsUser, Long, CmsUserRowVO>,
        DefaultControllerHasDeleteActionButton<CmsUser, Long>,
        DefaultControllerHasCreatePage<CmsUser, Long, CmsUserForm>,
        DefaultControllerHasEditPage<CmsUser, Long, CmsUserForm>,
        DefaultControllerHasDetailPage<CmsUser, Long> {

    @Autowired
    private CmsUserPageService cmsUserPageService;

    @Override
    protected BasePageService getPageService() {
        return cmsUserPageService;
    }

    @Override
    public DefaultServiceHasDataTable<CmsUser, Long, CmsUserRowVO> getDefaultPageHasDataTable() {
        return cmsUserPageService;
    }

    @Override
    public DefaultServiceHasCRUD<CmsUser, Long> getDefaultPageHasCRUD() {
        return cmsUserPageService;
    }

    @Override
    public DefaultServiceHasEditPage<CmsUser, Long, CmsUserForm> getDefaultPageHasEdit() {
        return cmsUserPageService;
    }

    @Override
    public DefaultServiceHasDetailPage<CmsUser, Long> getDefaultPageHasDetail() {
        return cmsUserPageService;
    }

    @Override
    public DefaultServiceHasCreatePage<CmsUser, Long, CmsUserForm> getDefaultPageHasCreate() {
        return cmsUserPageService;
    }
}
