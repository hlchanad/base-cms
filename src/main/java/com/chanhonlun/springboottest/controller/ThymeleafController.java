package com.chanhonlun.springboottest.controller;

import com.chanhonlun.springboottest.repository.SystemParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    @RequestMapping("/welcome")
    public String welcome(Map<String, Object> model) {
        model.put("message", "Hello World");
        model.put("systemParameters", systemParameterRepository.findByIsDeleteFalse());
        return "welcome";
    }

    @RequestMapping("/layout")
    public String layout(Map<String, Object> model) {
        return "content";
    }

}