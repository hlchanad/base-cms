package com.chanhonlun.basecms.response.vo;

import com.chanhonlun.basecms.constant.Language;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DetailField {

    private Language    language;
    private List<Field> fields;
}
