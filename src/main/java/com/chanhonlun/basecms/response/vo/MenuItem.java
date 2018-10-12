package com.chanhonlun.basecms.response.vo;

import com.chanhonlun.basecms.constant.MenuItemIconType;
import com.chanhonlun.basecms.pojo.CmsMenu;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
public class MenuItem {

    private String           title;
    private String           icon;
    private MenuItemIconType iconType;
    private String           url;
    private boolean          active;
    private List<MenuItem>   children;

    public MenuItem(CmsMenu cmsMenu, List<MenuItem> children, String contextPath) {

        this.title    = cmsMenu.getTitle();
        this.icon     = cmsMenu.getIcon();
        this.url      = StringUtils.isBlank(cmsMenu.getUrl()) ? null : contextPath + cmsMenu.getUrl();
        this.children = children;
        this.active = false;

        if (icon.startsWith("pg-"))      this.iconType = MenuItemIconType.PAGES;
        else if (icon.startsWith("fa-")) this.iconType = MenuItemIconType.FONT_AWESOME;
        else if (icon.startsWith("sl-")) this.iconType = MenuItemIconType.SIMPLE_LINE;
        else                             this.iconType = MenuItemIconType.TEXT;
    }

    public MenuItem(CmsMenu cmsMenu, List<MenuItem> children, String contextPath, boolean active) {

        this.title    = cmsMenu.getTitle();
        this.icon     = cmsMenu.getIcon();
        this.url      = StringUtils.isBlank(cmsMenu.getUrl()) ? null : contextPath + cmsMenu.getUrl();
        this.children = children;
        this.active = active;

        if (icon.startsWith("pg-"))      this.iconType = MenuItemIconType.PAGES;
        else if (icon.startsWith("fa-")) this.iconType = MenuItemIconType.FONT_AWESOME;
        else if (icon.startsWith("sl-")) this.iconType = MenuItemIconType.SIMPLE_LINE;
        else                             this.iconType = MenuItemIconType.TEXT;
    }
}
