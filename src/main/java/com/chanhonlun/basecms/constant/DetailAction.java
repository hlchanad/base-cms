package com.chanhonlun.basecms.constant;

import java.io.Serializable;

public enum DetailAction {

    DELETE("fa-trash-o", "/delete", "danger", true, ActionButtonType.DELETE),

    EDIT("fa-pencil", "/edit", "primary", true, ActionButtonType.REDIRECT),

    LIST("fa-list-ul", "", "complete", false, ActionButtonType.REDIRECT),

    ;

    private String faIcon;

    private String uri;

    private String bootstrapColor;

    private boolean insertId;

    private ActionButtonType type;

    DetailAction(String faIcon, String uri, String bootstrapColor, boolean insertId, ActionButtonType type) {
        this.faIcon = faIcon;
        this.uri = uri;
        this.bootstrapColor = bootstrapColor;
        this.insertId = insertId;
        this.type = type;
    }

    public String getFaIcon() {
        return faIcon;
    }

    public String getUri() {
        return uri;
    }

    public String getUri(String contextPath, String section, Serializable id) {
        return contextPath + "/" + section + (insertId ? "/" + id : "") + uri;
    }

    public String getBootstrapColor() {
        return bootstrapColor;
    }

    public boolean isInsertId() {
        return insertId;
    }

    public ActionButtonType getType() {
        return type;
    }

}
