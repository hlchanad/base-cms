package com.chanhonlun.basecms.vo;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

@Data
@AllArgsConstructor
public class BaseTableVO {

    @JsonView(DataTablesOutput.View.class)
    protected String action;

}
