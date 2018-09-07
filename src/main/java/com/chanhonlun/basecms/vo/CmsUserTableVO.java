package com.chanhonlun.basecms.vo;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public class CmsUserTableVO extends BaseTableVO {

    @JsonView(DataTablesOutput.View.class)
    private Long   id;

    @JsonView(DataTablesOutput.View.class)
    private String username;

    @JsonView(DataTablesOutput.View.class)
    private String email;

    @Builder
    public CmsUserTableVO(String action, Long id, String username, String email) {
        super(action);
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
