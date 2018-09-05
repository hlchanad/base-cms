package com.chanhonlun.basecms.constant;

public enum Action {

    DELETE("Delete", "/delete", "danger", ActionButtonType.DELETE),

    DETAIL("Detail", "/detail", "success", ActionButtonType.REDIRECT),

    EDIT("Edit", "/edit", "primary", ActionButtonType.REDIRECT),

    ;

    private String displayName;

    private String uri;

    private String bootstrapColor;

    private ActionButtonType type;

    Action(String displayName, String uri, String bootstrapColor, ActionButtonType type) {
        this.displayName = displayName;
        this.uri = uri;
        this.bootstrapColor = bootstrapColor;
        this.type = type;

    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUri() {
        return uri;
    }

    public String getBootstrapColor() {
        return bootstrapColor;
    }

    public ActionButtonType getType() {
        return type;
    }
}
