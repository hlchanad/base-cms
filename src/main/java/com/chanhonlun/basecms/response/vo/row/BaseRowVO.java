package com.chanhonlun.basecms.response.vo.row;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public class BaseRowVO {

    @JsonView(DataTablesOutput.View.class)
    protected String action;

    public BaseRowVO(String action) {
        this.action = action;
    }
}
