package com.chanhonlun.basecms.vo;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import java.io.DataOutput;

//@Data
//@EqualsAndHashCode(callSuper = true)
public class CmsMenuTableVO extends BaseTableVO {

    @JsonView(DataTablesOutput.View.class)
    private Long id;

    @JsonView(DataTablesOutput.View.class)
    private CmsMenuTableVO parent;

    @JsonView(DataTablesOutput.View.class)
    private String title;

    @JsonView(DataTablesOutput.View.class)
    private String url;

    @JsonView(DataTablesOutput.View.class)
    private String icon;

    @JsonView(DataTablesOutput.View.class)
    private Integer sequence;

    @Builder
    public CmsMenuTableVO(Long id, CmsMenuTableVO parent, String title, String url, String icon, Integer sequence, String action) {
        super(action);
        this.id = id;
        this.parent = parent;
        this.title = title;
        this.url = url;
        this.icon = icon;
        this.sequence = sequence;
    }
}
