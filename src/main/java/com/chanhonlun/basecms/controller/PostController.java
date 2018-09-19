package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDataTable;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDeleteActionButton;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDetailPageWithPojoDetail;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.form.PostForm;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.pojo.PostDetail;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.page.BaseService;
import com.chanhonlun.basecms.service.page.PostService;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDetailPageWithPojoDetail;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/post")
public class PostController extends BaseController implements
        DefaultControllerHasDeleteActionButton<Post, Long>,
        DefaultControllerHasDataTable<Post, Long, PostRowVO>,
//        DefaultControllerHasCreatePage,
        DefaultControllerHasDetailPageWithPojoDetail<Post, Long, PostDetail, Long> {

    @Autowired
    private PostService postService;

    @Override
    protected BaseService getService() {
        return postService;
    }

    @Override
    public DefaultServiceHasDataTable<Post, Long, PostRowVO> getDefaultPageHasDataTable() {
        return postService;
    }

    @Override
    public DefaultServiceHasCRUD<Post, Long> getDefaultPageHasCRUD() {
        return postService;
    }

    @Override
    public DefaultServiceHasDetailPageWithPojoDetail<Post, Long, PostDetail, Long> getDefaultPageHasDetail() {
        return postService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute(MyConstants.PAGE_RESPONSE, postService.getCreatePageConfig());
        return section + "/create";
    }

    @PostMapping("/create")
    public String doCreate(Model model, PostForm form) {

        FormError formError = postService.ifError(form);
        if (formError != null) {
            model.addAttribute(MyConstants.PAGE_RESPONSE, postService.getCreatePageConfig(form, formError));
            return section + "/create";
        }

        Post post = postService.create(form);

        return "redirect:/" + section + "/" + post.getId() + "/detail";
    }

}
