package com.chanhonlun.basecms.response.vo.row;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public class SystemParameterRowVO extends BaseRowVO {

    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @JsonView(DataTablesOutput.View.class)
    private String category;

    @JsonView(DataTablesOutput.View.class)
    private String key;

    @JsonView(DataTablesOutput.View.class)
    private String value;

    @JsonView(DataTablesOutput.View.class)
    private String description;

    @Builder
    public SystemParameterRowVO(String action, Long id, String category, String key, String value, String description) {
        super(action);
        this.id = id;
        this.category = category;
        this.key = key;
        this.value = value;
        this.description = description;
    }
}
