package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.PostRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.PostDataTableService;
import com.chanhonlun.basecms.service.page.PostService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl extends BaseServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostDataTableService postDataTableService;

    @Override
    public BaseRepository<Post, Long> getRepository() {
        return postRepository;
    }

    @Override
    public BaseDataTableService<Post, Long, PostRowVO, BaseDataTableInput, BaseDataTableConfig> getDataTablesService() {
        return postDataTableService;
    }

    @Override
    public BreadcrumbUtil getBreadcrumbUtil() {
        return breadcrumbUtil;
    }

    @Override
    public SidebarMenuUtil getSidebarMenuUtil() {
        return sidebarMenuUtil;
    }
}
