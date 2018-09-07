package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.req.datatables.CmsMenuListDataTablesInput;
import com.chanhonlun.basecms.service.CmsMenuService;
import com.chanhonlun.basecms.service.DefaultPageHasCRUD;
import com.chanhonlun.basecms.service.DefaultPageHasDataTable;
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
    public String getSection() {
        return "cms-menu";
    }

    @Override
    public DefaultPageHasDataTable<CmsMenu, Long, CmsMenuTableVO, CmsMenuListDataTablesInput, CmsMenuDataTablesVO> getDefaultPageHasDataTable() {
        return cmsMenuService;
    }

    @Override
    public DefaultPageHasCRUD<CmsMenu, Long> getDefaultPageHasCRUD() {
        return cmsMenuService;
    }

}
