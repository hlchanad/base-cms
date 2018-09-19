package com.chanhonlun.basecms.constant;

public enum NotificationBoxType {

    DANGER("Danger", "alert-danger"),

    WARNING("Danger", "alert-danger"),

    SUCCESS("Danger", "alert-danger"),

    INFO("Danger", "alert-danger"),

    ;

    private String displayText;
    private String clazz;

    NotificationBoxType(String displayText, String clazz) {
        this.displayText = displayText;
        this.clazz = clazz;
    }

    public String getDisplayText() {
        return displayText;
    }

    public String getClazz() {
        return clazz;
    }
}
