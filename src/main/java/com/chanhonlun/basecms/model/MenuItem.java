package com.chanhonlun.basecms.model;

import com.chanhonlun.basecms.constant.MenuItemIconType;
import com.chanhonlun.basecms.pojo.CmsMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class MenuItem {

    private Long id;
    private String title;
    private String icon;
    private MenuItemIconType iconType;
    private String url;
    private List<MenuItem> children;

    public MenuItem(CmsMenu cmsMenu, String contextPath) {

        this.id = cmsMenu.getId();
        this.title = cmsMenu.getTitle();
        this.icon = cmsMenu.getIcon();
        this.url = contextPath + cmsMenu.getUrl();
        this.children = new ArrayList<>();

        if (icon.startsWith("pg-"))      this.iconType = MenuItemIconType.PAGES;
        else if (icon.startsWith("fa-")) this.iconType = MenuItemIconType.FONT_AWESOME;
        else if (icon.startsWith("sl-")) this.iconType = MenuItemIconType.SIMPLE_LINE;
        else                             this.iconType = MenuItemIconType.TEXT;
    }
}
