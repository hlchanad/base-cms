package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.service.page.BasePageService;
import com.chanhonlun.basecms.service.page.LoginPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class LoginController extends BaseController {

    @Autowired
    private LoginPageService loginPageService;

    @GetMapping("/login")
    public String login(Model model) {
        return "login/login";
    }

    @Override
    protected BasePageService getPageService() {
        return loginPageService;
    }
}
