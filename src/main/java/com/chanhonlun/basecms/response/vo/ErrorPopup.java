package com.chanhonlun.basecms.response.vo;

import com.chanhonlun.basecms.constant.SweetAlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorPopup {

    public SweetAlertType type;

    public String title;

    public String text;

}
