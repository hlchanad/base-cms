package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

    @Autowired
    private DashboardService dashboardService;

    @RequestMapping("")
    public String dashboard(Model model) {
        model.addAttribute("CMS_RSP", dashboardService.getPageConfig());
        return "dashboard/dashboard";
    }
}
