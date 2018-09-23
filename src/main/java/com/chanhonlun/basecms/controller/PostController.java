package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasCreatePageWithPojoDetail;
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
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCreatePageWithPojoDetail;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDetailPageWithPojoDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController extends BaseController implements
        DefaultControllerHasDeleteActionButton<Post, Long>,
        DefaultControllerHasDataTable<Post, Long, PostRowVO>,
        DefaultControllerHasCreatePageWithPojoDetail<Post, Long, PostForm>,
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

    @Override
    public DefaultServiceHasCreatePageWithPojoDetail<Post, Long, PostForm> getDefaultPageHasCreate() {
        return postService;
    }

    @GetMapping(value = "/{id}/edit")
    public String edit(Model model, @PathVariable(value = "id") Long id) {

        Post post = postService.findByIdAndIsDeleteFalse(id);

        if (post == null) {
            return "redirect:/" + getSection();
        }

        model.addAttribute(MyConstants.PAGE_RESPONSE, postService.getEditPageConfig(post));
        return getSection() + "/edit";
    }

    @PutMapping(value = "/{id}/edit")
    public String doEdit(Model model, @PathVariable(value = "id") Long id, PostForm form) {

        Post post = postService.findByIdAndIsDeleteFalse(id);

        if (post == null) {
            return "redirect:/" + getSection();
        }

        FormError formError = postService.ifEditError(post, form);
        if (formError != null) {
            model.addAttribute(MyConstants.PAGE_RESPONSE, postService.getEditPageConfig(post, form, formError));
            return getSection() + "/edit";
        }

        postService.edit(post, form);

        return "redirect:/" + getSection() + "/" + id + "/detail";
    }
}
