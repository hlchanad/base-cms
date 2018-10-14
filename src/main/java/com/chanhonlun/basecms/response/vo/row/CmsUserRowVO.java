package com.chanhonlun.basecms.response.vo.row;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public class CmsUserRowVO extends BaseRowVO {

    @JsonView(DataTablesOutput.View.class)
    private Long   id;

    @JsonView(DataTablesOutput.View.class)
    private String username;

    @JsonView(DataTablesOutput.View.class)
    private String email;

    @JsonView(DataTablesOutput.View.class)
    private String roles;

    @Builder
    public CmsUserRowVO(String action, Long id, String username, String email, String roles) {
        super(action);
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
