package com.chanhonlun.basecms.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataTablesColumn {

    private String data;
    private String title;
    @Builder.Default
    private Boolean orderable = true;
    @Builder.Default
    private Boolean searchable = true;

}
