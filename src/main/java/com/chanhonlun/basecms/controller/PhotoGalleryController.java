package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.service.page.BasePageService;
import com.chanhonlun.basecms.service.page.PhotoGalleryPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/photo-gallery")
public class PhotoGalleryController extends BaseController {

    @Autowired
    private PhotoGalleryPageService photoGalleryPageService;

    @Override
    protected BasePageService getPageService() {
        return photoGalleryPageService;
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute(MyConstants.PAGE_RESPONSE, photoGalleryPageService.getListConfig());
        return "photo-gallery/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute(MyConstants.PAGE_RESPONSE, photoGalleryPageService.getCreateConfig());
        return "photo-gallery/create";
    }
}
