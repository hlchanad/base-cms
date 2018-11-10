package com.chanhonlun.basecms.service.datatable.impl;

import com.chanhonlun.basecms.constant.ListAction;
import com.chanhonlun.basecms.model.UserPrincipal;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.repository.CmsUserRepository;
import com.chanhonlun.basecms.response.vo.DataTableColumn;
import com.chanhonlun.basecms.response.vo.row.CmsUserRowVO;
import com.chanhonlun.basecms.service.datatable.DefaultDataTableService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CmsUserDataTableServiceImpl extends BaseDataTableServiceImpl implements
        DefaultDataTableService<CmsUser, Long, CmsUserRowVO> {

    @Autowired
    private CmsUserRepository cmsUserRepository;

    @Override
    public DataTablesRepository<CmsUser, Long> getDataTablesRepository() {
        return cmsUserRepository;
    }

    @Override
    public String getPageTitle() {
        return "CMS User";
    }

    @Override
    public CmsUserRowVO getTableVOFromPOJO(CmsUser cmsUser) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        List<ListAction> actions = (userPrincipal.getCmsUser().getId().equals(cmsUser.getId()))
                ? Arrays.asList(ListAction.DETAIL, ListAction.EDIT)
                : Arrays.asList(ListAction.DETAIL, ListAction.EDIT, ListAction.DELETE);

        return CmsUserRowVO.builder()
                .id(cmsUser.getId())
                .username(cmsUser.getUsername())
                .email(cmsUser.getEmail())
                .roles(cmsUser.getRoles().stream().map(Role::getTitle).collect(Collectors.joining(", ")))
                .action(new Gson().toJson(listActionButtonUtil.get(cmsUser.getId(), actions)))
                .build();
    }

    @Override
    public List<DataTableColumn> getDataTableColumns() {
        return Arrays.asList(
                DataTableColumn.builder().data("id").title("ID").build(),
                DataTableColumn.builder().data("username").title("Username").build(),
                DataTableColumn.builder().data("email").title("Email").build(),
                DataTableColumn.builder().data("roles").title("Roles").orderable(false).build(),
                DataTableColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );
    }
}
