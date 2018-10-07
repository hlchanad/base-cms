package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.*;
import com.chanhonlun.basecms.form.CmsMenuForm;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.response.vo.row.CmsMenuRowVO;
import com.chanhonlun.basecms.service.page.BaseService;
import com.chanhonlun.basecms.service.page.CmsMenuService;
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
    private CmsMenuService cmsMenuService;

    @Override
    protected BaseService getService() {
        return cmsMenuService;
    }

    @Override
    public DefaultServiceHasDataTable<CmsMenu, Long, CmsMenuRowVO> getDefaultPageHasDataTable() {
        return cmsMenuService;
    }

    @Override
    public DefaultServiceHasCRUD<CmsMenu, Long> getDefaultPageHasCRUD() {
        return cmsMenuService;
    }

    @Override
    public DefaultServiceHasEditPage<CmsMenu, Long, CmsMenuForm> getDefaultPageHasEdit() {
        return cmsMenuService;
    }

    @Override
    public DefaultServiceHasDetailPage<CmsMenu, Long> getDefaultPageHasDetail() {
        return cmsMenuService;
    }

    @Override
    public DefaultServiceHasCreatePage<CmsMenu, Long, CmsMenuForm> getDefaultPageHasCreate() {
        return cmsMenuService;
    }
}
