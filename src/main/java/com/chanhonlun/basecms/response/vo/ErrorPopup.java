package com.chanhonlun.basecms.response.vo;

import com.chanhonlun.basecms.constant.SweetAlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorPopup {

    private SweetAlertType type;

    private String title;

    private String text;

}
