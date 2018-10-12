package com.chanhonlun.basecms.response.vo;

import com.chanhonlun.basecms.constant.FieldType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Field {

    public String id;
    public String title;
    public FieldType type;
    public String value;
    public Float numberStep;
    public String placeholder;
    public String hintTitle;
    public String hintDetail;
    public List<FieldOption> options;
    @Builder.Default public boolean required = true;
    @Builder.Default public boolean disabled = false;
    @Builder.Default public boolean show = true;
}
