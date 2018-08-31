package com.chanhonlun.basecms.vo;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

//@Data
//@EqualsAndHashCode(callSuper = true)
public class SystemParameterTableVO extends BaseTableVO {

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
    public SystemParameterTableVO(String action, Long id, String category, String key, String value, String description) {
        super(action);
        this.id = id;
        this.category = category;
        this.key = key;
        this.value = value;
        this.description = description;
    }
}
