package com.chanhonlun.basecms.response;

import com.chanhonlun.basecms.constant.FieldType;
import lombok.Builder;

import java.util.List;

@Builder
public class Field {

    public String id;
    public String title;
    public FieldType type;
    public String value;
    public String placeholder;
    public String hintTitle;
    public String hintDetail;
    public List<FieldOption> options;
    @Builder.Default public boolean required = true;
    @Builder.Default public boolean disabled = false;
    @Builder.Default public boolean show = true;
}
