package com.chanhonlun.basecms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController extends BaseController {

    @RequestMapping("")
    public String dashboard(Model model) {
        return "layouts/blank";
    }
}
