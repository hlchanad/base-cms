package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.*;
import com.chanhonlun.basecms.form.PostForm;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.pojo.PostDetail;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.page.BasePageService;
import com.chanhonlun.basecms.service.page.PostPageService;
import com.chanhonlun.basecms.service.trait.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
public class PostController extends BaseController implements
        DefaultControllerHasDeleteActionButton<Post, Long>,
        DefaultControllerHasDataTable<Post, Long, PostRowVO>,
        DefaultControllerHasCreatePageWithPojoDetail<Post, Long, PostForm>,
        DefaultControllerHasEditPageWithPojoDetail<Post, Long, PostDetail, Long, PostForm>,
        DefaultControllerHasDetailPageWithPojoDetail<Post, Long, PostDetail, Long> {

    @Autowired
    private PostPageService postPageService;

    @Override
    protected BasePageService getPageService() {
        return postPageService;
    }

    @Override
    public DefaultServiceHasCRUD<Post, Long> getDefaultPageHasCRUD() {
        return postPageService;
    }

    @Override
    public DefaultServiceHasDataTable<Post, Long, PostRowVO> getDefaultPageHasDataTable() {
        return postPageService;
    }

    @Override
    public DefaultServiceHasCreatePageWithPojoDetail<Post, Long, PostForm> getDefaultPageHasCreate() {
        return postPageService;
    }

    @Override
    public DefaultServiceHasEditPageWithPojoDetail<Post, Long, PostDetail, Long, PostForm> getDefaultPageHasEdit() {
        return postPageService;
    }

    @Override
    public DefaultServiceHasDetailPageWithPojoDetail<Post, Long, PostDetail, Long> getDefaultPageHasDetail() {
        return postPageService;
    }

}
