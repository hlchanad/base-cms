package com.chanhonlun.basecms.response;

import com.chanhonlun.basecms.constant.DetailButtonType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetailButton {

    private DetailButtonType type;
    private String bootstrapColor;
    private String faIcon;
    private String href;
    private String redirectUrl; // only for type == DetailButtonType.DELETE
}
