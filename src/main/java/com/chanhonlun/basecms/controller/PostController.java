package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDataTable;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDeleteActionButton;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.page.PostService;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
