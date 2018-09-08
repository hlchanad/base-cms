package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDataTable;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDeleteActionButton;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.service.page.CmsMenuService;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.vo.row.CmsMenuRowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms-menu")
public class CmsMenuController extends BaseController implements
        DefaultControllerHasDataTable<CmsMenu, Long, CmsMenuRowVO>,
        DefaultControllerHasDeleteActionButton<CmsMenu, Long> {

    @Autowired
    private CmsMenuService cmsMenuService;

    @Override
    public DefaultServiceHasDataTable<CmsMenu, Long, CmsMenuRowVO> getDefaultPageHasDataTable() {
        return cmsMenuService;
    }

    @Override
    public DefaultServiceHasCRUD<CmsMenu, Long> getDefaultPageHasCRUD() {
        return cmsMenuService;
    }

}
