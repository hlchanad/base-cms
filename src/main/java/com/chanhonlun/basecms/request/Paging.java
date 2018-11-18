package com.chanhonlun.basecms.request;

import com.chanhonlun.basecms.constraint.annotation.Enum;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class Paging {

    private String sortBy;

    @Enum(enumClass = Sort.Direction.class)
    private String direction;

    private Integer page;

    private Integer limit;
}
