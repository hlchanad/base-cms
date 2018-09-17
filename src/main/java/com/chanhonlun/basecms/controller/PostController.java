package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDataTable;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDeleteActionButton;
import com.chanhonlun.basecms.form.PostForm;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.page.PostService;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/post")
public class PostController extends BaseController implements
        DefaultControllerHasDeleteActionButton<Post, Long>,
        DefaultControllerHasDataTable<Post, Long, PostRowVO> {

    @Autowired
    private PostService postService;

    @Override
    public DefaultServiceHasDataTable<Post, Long, PostRowVO> getDefaultPageHasDataTable() {
        return postService;
    }

    @Override
    public DefaultServiceHasCRUD<Post, Long> getDefaultPageHasCRUD() {
        return postService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute(MyConstants.PAGE_RESPONSE, postService.getCreatePageConfig());
        return "post/create";
    }

    @PostMapping("/create")
    public String doCreate(Model model, HttpServletRequest httpServletRequest, @Valid PostForm form, BindingResult bindingResult) {
        logger.info("form data: {}", new Gson().toJson(httpServletRequest.getParameterMap()));
        logger.info("form: {}", new Gson().toJson(form));
        logger.info("bindingResult: {}", new Gson().toJson(bindingResult));

        Post post = postService.create(form);

        model.addAttribute(MyConstants.PAGE_RESPONSE, postService.getDetailPageConfig(post));
        return "post/create";
    }

    @GetMapping("/{id}/detail")
    public String detail(Model model, @PathVariable(value = "id") Long id) {
        model.addAttribute(MyConstants.PAGE_RESPONSE, postService.getDetailPageConfig(id));
        return "post/detail";
    }
}
