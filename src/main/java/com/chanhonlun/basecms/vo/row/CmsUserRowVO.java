package com.chanhonlun.basecms.vo.row;

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

    @Builder
    public CmsUserRowVO(String action, Long id, String username, String email) {
        super(action);
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
