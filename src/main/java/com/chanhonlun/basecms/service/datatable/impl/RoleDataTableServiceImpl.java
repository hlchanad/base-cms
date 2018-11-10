package com.chanhonlun.basecms.service.datatable.impl;

import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.repository.RoleRepository;
import com.chanhonlun.basecms.response.vo.DataTableColumn;
import com.chanhonlun.basecms.response.vo.row.RoleRowVO;
import com.chanhonlun.basecms.service.datatable.DefaultDataTableService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleDataTableServiceImpl extends BaseDataTableServiceImpl implements
        DefaultDataTableService<Role, Long, RoleRowVO> {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public DataTablesRepository<Role, Long> getDataTablesRepository() {
        return roleRepository;
    }

    @Override
    public RoleRowVO getTableVOFromPOJO(Role role) {
        return RoleRowVO.builder()
                .id(role.getId())
                .code(role.getCode())
                .title(role.getTitle())
                .description(role.getDescription())
                .action(new Gson().toJson(listActionButtonUtil.get(role.getId())))
                .build();
    }

    @Override
    public List<DataTableColumn> getDataTableColumns() {
        return Arrays.asList(
                DataTableColumn.builder().data("id").title("ID").build(),
                DataTableColumn.builder().data("code").title("Code").build(),
                DataTableColumn.builder().data("title").title("Title").build(),
                DataTableColumn.builder().data("description").title("Description").build(),
                DataTableColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );
    }
}
