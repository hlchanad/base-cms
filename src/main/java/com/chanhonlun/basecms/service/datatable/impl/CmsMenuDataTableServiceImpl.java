package com.chanhonlun.basecms.service.datatable.impl;

import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.repository.CmsMenuRepository;
import com.chanhonlun.basecms.response.DataTableColumn;
import com.chanhonlun.basecms.response.vo.row.CmsMenuRowVO;
import com.chanhonlun.basecms.service.datatable.DefaultDataTableService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CmsMenuDataTableServiceImpl extends BaseDataTableServiceImpl implements
        DefaultDataTableService<CmsMenu, Long, CmsMenuRowVO> {

    @Autowired
    private CmsMenuRepository cmsMenuRepository;

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
    public List<DataTableColumn> getDataTableColumns() {
        return Arrays.asList(
                DataTableColumn.builder().data("id").title("ID").build(),
                DataTableColumn.builder().data("parent.title").title("Parent").build(),
                DataTableColumn.builder().data("title").title("Title").build(),
                DataTableColumn.builder().data("url").title("URL").build(),
                DataTableColumn.builder().data("icon").title("Icon").build(),
                DataTableColumn.builder().data("sequence").title("Sequence").build(),
                DataTableColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );
    }
}
