package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.*;
import com.chanhonlun.basecms.form.CmsMenuForm;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.response.vo.row.CmsMenuRowVO;
import com.chanhonlun.basecms.service.page.BasePageService;
import com.chanhonlun.basecms.service.page.CmsMenuPageService;
import com.chanhonlun.basecms.service.trait.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms-menu")
public class CmsMenuController extends BaseController implements
        DefaultControllerHasDataTable<CmsMenu, Long, CmsMenuRowVO>,
        DefaultControllerHasDeleteActionButton<CmsMenu, Long>,
        DefaultControllerHasCreatePage<CmsMenu, Long, CmsMenuForm>,
        DefaultControllerHasEditPage<CmsMenu, Long, CmsMenuForm>,
        DefaultControllerHasDetailPage<CmsMenu, Long> {

    @Autowired
    private CmsMenuPageService cmsMenuPageService;

    @Override
    protected BasePageService getPageService() {
        return cmsMenuPageService;
    }

    @Override
    public DefaultServiceHasDataTable<CmsMenu, Long, CmsMenuRowVO> getDefaultPageHasDataTable() {
        return cmsMenuPageService;
    }

    @Override
    public DefaultServiceHasCRUD<CmsMenu, Long> getDefaultPageHasCRUD() {
        return cmsMenuPageService;
    }

    @Override
    public DefaultServiceHasEditPage<CmsMenu, Long, CmsMenuForm> getDefaultPageHasEdit() {
        return cmsMenuPageService;
    }

    @Override
    public DefaultServiceHasDetailPage<CmsMenu, Long> getDefaultPageHasDetail() {
        return cmsMenuPageService;
    }

    @Override
    public DefaultServiceHasCreatePage<CmsMenu, Long, CmsMenuForm> getDefaultPageHasCreate() {
        return cmsMenuPageService;
    }
}
