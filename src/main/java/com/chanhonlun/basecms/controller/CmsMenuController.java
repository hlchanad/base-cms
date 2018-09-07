package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.req.datatables.CmsMenuListDataTablesInput;
import com.chanhonlun.basecms.service.CmsMenuService;
import com.chanhonlun.basecms.service.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.vo.CmsMenuDataTablesVO;
import com.chanhonlun.basecms.vo.CmsMenuTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cms-menu")
public class CmsMenuController extends BaseController implements
        DefaultControllerHasDataTable<CmsMenu, Long, CmsMenuTableVO, CmsMenuListDataTablesInput, CmsMenuDataTablesVO>,
        DefaultControllerHasDeleteActionButton<CmsMenu, Long> {

    @Autowired
    private CmsMenuService cmsMenuService;

    @Override
    public DefaultServiceHasDataTable<CmsMenu, Long, CmsMenuTableVO, CmsMenuListDataTablesInput, CmsMenuDataTablesVO> getDefaultPageHasDataTable() {
        return cmsMenuService;
    }

    @Override
    public DefaultServiceHasCRUD<CmsMenu, Long> getDefaultPageHasCRUD() {
        return cmsMenuService;
    }

}
