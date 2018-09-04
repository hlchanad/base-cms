package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.constant.ActionButtonType;
import com.chanhonlun.basecms.model.ActionButton;
import com.chanhonlun.basecms.model.DataTablesColumn;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.repository.CmsMenuRepository;
import com.chanhonlun.basecms.req.datatables.CmsMenuListDataTablesInput;
import com.chanhonlun.basecms.service.DataTablesServiceTrait;
import com.chanhonlun.basecms.vo.CmsMenuDataTablesVO;
import com.chanhonlun.basecms.vo.CmsMenuTableVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CmsMenuDataTablesService extends BaseDataTablesService implements
        DataTablesServiceTrait<CmsMenu, Long, CmsMenuTableVO, CmsMenuListDataTablesInput, CmsMenuDataTablesVO> {

    @Autowired
    private CmsMenuRepository cmsMenuRepository;

    @Override
    public DataTablesRepository<CmsMenu, Long> getDataTablesRepository() {
        return cmsMenuRepository;
    }

    @Override
    public CmsMenuTableVO getTableVOFromPOJO(CmsMenu cmsMenu) {

        List<ActionButton> actionButtons = Arrays.asList(
                ActionButton.builder()
                        .type(ActionButtonType.REDIRECT)
                        .displayName("Detail")
                        .bootstrapColor("success")
                        .href(contextPath + "/cms-menu/" + cmsMenu.getId() + "/detail")
                        .build(),
                ActionButton.builder()
                        .type(ActionButtonType.REDIRECT)
                        .displayName("Edit")
                        .bootstrapColor("primary")
                        .href(contextPath + "/cms-menu/" + cmsMenu.getId() + "/edit")
                        .build(),
                ActionButton.builder()
                        .type(ActionButtonType.DELETE)
                        .displayName("Delete")
                        .bootstrapColor("danger")
                        .href(contextPath + "/cms-menu/" + cmsMenu.getId() + "/delete")
                        .build()
        );

        return CmsMenuTableVO.builder()
                .id(cmsMenu.getId())
                .parent(CmsMenuTableVO.builder()
                        .title(cmsMenu.getParent() == null ? null : cmsMenu.getParent().getTitle())
                        .build())
                .title(cmsMenu.getTitle())
                .url(cmsMenu.getUrl())
                .icon(cmsMenu.getIcon())
                .sequence(cmsMenu.getSequence())
                .action(new Gson().toJson(actionButtons))
                .build();
    }

    @Override
    public CmsMenuDataTablesVO getDataTablesConfig(Map<String, String> extraConfigs) {

        List<DataTablesColumn> dataTablesColumns = Arrays.asList(
                DataTablesColumn.builder().data("id").title("ID").build(),
                DataTablesColumn.builder().data("parent.title").title("Parent").build(),
                DataTablesColumn.builder().data("title").title("Title").build(),
                DataTablesColumn.builder().data("url").title("URL").build(),
                DataTablesColumn.builder().data("icon").title("Icon").build(),
                DataTablesColumn.builder().data("sequence").title("Sequence").build(),
                DataTablesColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );

        return CmsMenuDataTablesVO.builder()
                .title("CMS Menu")
                .dataTablesId("cms-menu")
                .ajaxUrl(contextPath + "/cms-menu/data")
                .dataTablesColumns(dataTablesColumns)
                .extraConfigs(extraConfigs)
                .build();
    }
}
