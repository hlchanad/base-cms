package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.service.page.BasePageService;
import com.chanhonlun.basecms.service.page.DashboardPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

    @Autowired
    private DashboardPageService dashboardPageService;

    @RequestMapping("")
    public String dashboard(Model model) {
        model.addAttribute(MyConstants.PAGE_RESPONSE, dashboardPageService.getPageConfig());
        return "dashboard/dashboard";
    }

    @Override
    protected BasePageService getPageService() {
        return dashboardPageService;
    }
}
