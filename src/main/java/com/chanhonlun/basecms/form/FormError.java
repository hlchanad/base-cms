package com.chanhonlun.basecms.form;

import com.chanhonlun.basecms.constant.NotificationBoxType;

public class FormError {

    public NotificationBoxType type;
    public String              text;

    public FormError(String text) {
        this.text = text;
        this.type = NotificationBoxType.DANGER;
    }

    public FormError(NotificationBoxType type, String text) {
        this.type = type;
        this.text = text;
    }
}
