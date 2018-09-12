package com.chanhonlun.basecms.response.vo.row;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

@Builder
public class PostDetailRowVO {

    @JsonView(DataTablesOutput.View.class)
    private String title;

    @JsonView(DataTablesOutput.View.class)
    private String brief;

}
