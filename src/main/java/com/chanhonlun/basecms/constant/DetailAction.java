package com.chanhonlun.basecms.constant;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public enum DetailAction {

    DELETE("fa-trash-o", "danger", "/delete", true, "", false, ActionButtonType.DELETE),

    EDIT("fa-pencil", "primary", "/edit", true, ActionButtonType.REDIRECT),

    LIST("fa-list-ul", "complete", "", false, ActionButtonType.REDIRECT),;

    private String faIcon;

    private String bootstrapColor;

    private String uri;

    private boolean insertId;

    private String redirectUrl;

    private boolean insertIdForRedirect;

    private ActionButtonType type;

    DetailAction(String faIcon, String bootstrapColor, String uri, boolean insertId, ActionButtonType type) {
        this.faIcon = faIcon;
        this.bootstrapColor = bootstrapColor;
        this.uri = uri;
        this.insertId = insertId;
        this.type = type;
    }

    DetailAction(String faIcon, String bootstrapColor, String uri, boolean insertId, String redirectUrl, boolean insertIdForRedirect, ActionButtonType type) {
        this.faIcon = faIcon;
        this.bootstrapColor = bootstrapColor;
        this.uri = uri;
        this.insertId = insertId;
        this.redirectUrl = redirectUrl;
        this.insertIdForRedirect = insertIdForRedirect;
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

    public String getRedirectUrl(String contextPath, String section, Serializable id) {
        return StringUtils.isBlank(redirectUrl)
                ? contextPath + "/" + section + (insertIdForRedirect ? "/" + id : "") + redirectUrl
                : null;
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
