package com.chanhonlun.basecms.service.datatable.impl;

import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.repository.PostRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.DataTableColumn;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.component.DefaultDataTableConfig;
import com.chanhonlun.basecms.response.vo.row.PostDetailRowVO;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.google.gson.Gson;
import com.mysema.codegen.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class PostDataTableServiceImpl extends BaseDataTableServiceImpl implements
        BaseDataTableService<Post, Long, PostRowVO, BaseDataTableInput, BaseDataTableConfig> {

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
                .publishDate(post.getPublishDate())
                .detailEn(PostDetailRowVO.builder()
                        .title(post.getDetailEn().getTitle())
                        .brief(post.getDetailEn().getBrief())
                        .build())
                .action(new Gson().toJson(actionButtonUtil.get(post.getId())))
                .build();
    }

    @Override
    public BaseDataTableConfig getDataTableConfig(Map<String, String> extraConfigs) {

        List<DataTableColumn> dataTableColumns = Arrays.asList(
                DataTableColumn.builder().data("id").title("ID").build(),
                DataTableColumn.builder().data("detailEn.title").title("Title").build(),
                DataTableColumn.builder().data("detailEn.brief").title("Brief").build(),
                DataTableColumn.builder().data("publishDate").title("Publish Date").build(),
                DataTableColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );

        return DefaultDataTableConfig.builder()
                .title(StringUtils.capitalize(section.replace("-", " ")))
                .dataTableId(section)
                .ajaxUrl(contextPath + "/" + section + "/data")
                .dataTableColumns(dataTableColumns)
                .extraConfigs(extraConfigs)
                .build();
    }

}
