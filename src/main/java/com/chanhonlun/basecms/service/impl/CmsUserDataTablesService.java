package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.model.DataTablesColumn;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.repository.CmsUserRepository;
import com.chanhonlun.basecms.req.datatables.CmsUserListDataTablesInput;
import com.chanhonlun.basecms.service.DataTablesServiceTrait;
import com.chanhonlun.basecms.vo.CmsUserDataTablesVO;
import com.chanhonlun.basecms.vo.CmsUserTableVO;
import com.chanhonlun.basecms.vo.SystemParameterDataTablesVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CmsUserDataTablesService extends BaseDataTablesService implements DataTablesServiceTrait<CmsUser,Long,CmsUserTableVO,CmsUserListDataTablesInput,CmsUserDataTablesVO> {

    @Autowired
    private CmsUserRepository cmsUserRepository;

    @Override
    public DataTablesRepository<CmsUser, Long> getDataTablesRepository() {
        return cmsUserRepository;
    }

    @PostConstruct
    public void init() {
        actionButtonUtil.init("cms-user");
    }

    @Override
    public CmsUserTableVO getTableVOFromPOJO(CmsUser cmsUser) {

        return CmsUserTableVO.builder()
                .id(cmsUser.getId())
                .username(cmsUser.getUsername())
                .email(cmsUser.getEmail())
                .action(new Gson().toJson(actionButtonUtil.get(cmsUser.getId())))
                .build();
    }

    @Override
    public CmsUserDataTablesVO getDataTablesConfig(Map<String, String> extraConfigs) {

        List<DataTablesColumn> dataTablesColumns = Arrays.asList(
                DataTablesColumn.builder().data("id").title("ID").build(),
                DataTablesColumn.builder().data("username").title("Username").build(),
                DataTablesColumn.builder().data("email").title("Email").build(),
                DataTablesColumn.builder().data("action").title("Action").orderable(false).searchable(false).build()
        );

        return CmsUserDataTablesVO.builder()
                .title("CMS User")
                .dataTablesId("cms-user")
                .ajaxUrl(contextPath + "/cms-user/data")
                .dataTablesColumns(dataTablesColumns)
                .extraConfigs(extraConfigs)
                .build();
    }
}
