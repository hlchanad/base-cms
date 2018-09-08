package com.chanhonlun.basecms.service.datatable.impl;

import com.chanhonlun.basecms.model.DataTableColumn;
import com.chanhonlun.basecms.model.component.BaseDataTableConfig;
import com.chanhonlun.basecms.model.component.DefaultDataTableConfig;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.repository.CmsMenuRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.vo.row.CmsMenuRowVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CmsMenuDataTableServiceImpl extends BaseDataTableServiceImpl implements
        BaseDataTableService<CmsMenu, Long, CmsMenuRowVO, BaseDataTableInput, BaseDataTableConfig> {

    @Autowired
    private CmsMenuRepository cmsMenuRepository;

    @PostConstruct
    public void init() {
        actionButtonUtil.init("cms-menu");
    }

    @Override
    public DataTablesRepository<CmsMenu, Long> getDataTablesRepository() {
        return cmsMenuRepository;
    }

    @Override
    public CmsMenuRowVO getTableVOFromPOJO(CmsMenu cmsMenu) {

        return CmsMenuRowVO.builder()
                .id(cmsMenu.getId())
                .parent(CmsMenuRowVO.builder()
                        .title(cmsMenu.getParent() == null ? null : cmsMenu.getParent().getTitle())
                        .build())
                .title(cmsMenu.getTitle())
                .url(cmsMenu.getUrl())
                .icon(cmsMenu.getIcon())
                .sequence(cmsMenu.getSequence())
                .action(new Gson().toJson(actionButtonUtil.get(cmsMenu.getId())))
                .build();
    }

    @Override
    public BaseDataTableConfig getDataTablesConfig(Map<String, String> extraConfigs) {

        List<DataTableColumn> dataTableColumns = Arrays.asList(
                DataTableColumn.builder().data("id").title("ID").build(),
                DataTableColumn.builder().data("parent.title").title("Parent").build(),
                DataTableColumn.builder().data("title").title("Title").build(),
                DataTableColumn.builder().data("url").title("URL").build(),
                DataTableColumn.builder().data("icon").title("Icon").build(),
                DataTableColumn.builder().data("sequence").title("Sequence").build(),
                DataTableColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );

        return DefaultDataTableConfig.builder()
                .title("CMS Menu")
                .dataTableId("cms-menu")
                .ajaxUrl(contextPath + "/cms-menu/data")
                .dataTableColumns(dataTableColumns)
                .extraConfigs(extraConfigs)
                .build();
    }
}
