package com.chanhonlun.basecms.response.vo;

import com.chanhonlun.basecms.constant.FieldType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Field {

    private String id;
    private String title;
    private FieldType type;
    private String value;
    private List<String> multiValues;
    private Float numberStep;
    private String placeholder;
    private String hintTitle;
    private String hintDetail;
    private List<FieldOption> options;
    @Builder.Default private boolean required = true;
    @Builder.Default private boolean disabled = false;
    @Builder.Default private boolean show = true;
}
