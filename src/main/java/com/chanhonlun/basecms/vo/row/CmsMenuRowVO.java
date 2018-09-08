package com.chanhonlun.basecms.vo.row;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

public class CmsMenuRowVO extends BaseRowVO {

    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @JsonView(DataTablesOutput.View.class)
    private CmsMenuRowVO parent;

    @JsonView(DataTablesOutput.View.class)
    private String title;

    @JsonView(DataTablesOutput.View.class)
    private String url;

    @JsonView(DataTablesOutput.View.class)
    private String icon;

    @JsonView(DataTablesOutput.View.class)
    private Integer sequence;

    @Builder
    public CmsMenuRowVO(Long id, CmsMenuRowVO parent, String title, String url, String icon, Integer sequence, String action) {
        super(action);
        this.id = id;
        this.parent = parent;
        this.title = title;
        this.url = url;
        this.icon = icon;
        this.sequence = sequence;
    }
}
