package com.chanhonlun.basecms.response.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DataTableColumn {

    private String data;
    private String title;
    @Builder.Default
    private Boolean orderable = true;
    @Builder.Default
    private Boolean searchable = true;

}
