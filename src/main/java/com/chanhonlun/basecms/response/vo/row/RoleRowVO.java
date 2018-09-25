package com.chanhonlun.basecms.response.vo.row;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public class RoleRowVO extends BaseRowVO {

    @JsonView(DataTablesOutput.View.class)
    public Long id;

    @JsonView(DataTablesOutput.View.class)
    public String code;

    @JsonView(DataTablesOutput.View.class)
    public String title;

    @JsonView(DataTablesOutput.View.class)
    public String description;

    @Builder
    public RoleRowVO(String action, Long id, String code, String title, String description) {
        super(action);
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
    }
}
