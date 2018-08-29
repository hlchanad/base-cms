package com.chanhonlun.springboottest.model;

import com.chanhonlun.springboottest.constant.ActionButtonType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionButton {

    private ActionButtonType type;
    private String           displayName;
    private String           bootstrapColor;
    private String           href;
}
