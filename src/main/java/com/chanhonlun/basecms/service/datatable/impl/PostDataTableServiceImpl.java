package com.chanhonlun.basecms.service.datatable.impl;

import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.repository.PostRepository;
import com.chanhonlun.basecms.response.vo.DataTableColumn;
import com.chanhonlun.basecms.response.vo.row.PostDetailRowVO;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.datatable.DefaultDataTableService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PostDataTableServiceImpl extends BaseDataTableServiceImpl implements
        DefaultDataTableService<Post, Long, PostRowVO> {

    @Autowired
    private PostRepository postRepository;

    @Override
    public DataTablesRepository<Post, Long> getDataTablesRepository() {
        return postRepository;
    }

    @Override
    public PostRowVO getTableVOFromPOJO(Post post) {
        return PostRowVO.builder()
                .id(post.getId())
                .publishDate(post.getPublishDate().toString())
                .detailEn(PostDetailRowVO.builder()
                        .title(post.getDetailEn().getTitle())
                        .brief(post.getDetailEn().getBrief())
                        .build())
                .action(new Gson().toJson(listActionButtonUtil.get(post.getId())))
                .build();
    }

    @Override
    public List<DataTableColumn> getDataTableColumns() {
        return Arrays.asList(
                DataTableColumn.builder().data("id").title("ID").build(),
                DataTableColumn.builder().data("detailEn.title").title("Title").build(),
                DataTableColumn.builder().data("detailEn.brief").title("Brief").build(),
                DataTableColumn.builder().data("publishDate").title("Publish Date").build(),
                DataTableColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );
    }
}
