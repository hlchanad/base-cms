package com.chanhonlun.basecms.response.vo.row;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public class PostRowVO extends BaseRowVO {

    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @JsonView(DataTablesOutput.View.class)
    private String publishDate;

    @JsonView(DataTablesOutput.View.class)
    private PostDetailRowVO detailEn;

    @Builder
    public PostRowVO(String action, Long id, String publishDate, PostDetailRowVO detailEn) {
        super(action);
        this.id = id;
        this.publishDate = publishDate;
        this.detailEn = detailEn;
    }
}
