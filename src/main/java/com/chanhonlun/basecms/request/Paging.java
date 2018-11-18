package com.chanhonlun.basecms.request;

import com.chanhonlun.basecms.constraint.annotation.Enum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paging {

    private String sortBy;

    @Enum(enumClass = Sort.Direction.class)
    private String direction;

    private Integer page;

    private Integer limit;
}
