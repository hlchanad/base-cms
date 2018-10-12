package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.response.vo.MenuItem;
import com.chanhonlun.basecms.service.page.CmsMenuService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SidebarMenuUtil {

    private static final Logger logger = LoggerFactory.getLogger(SidebarMenuUtil.class);

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private CmsMenuService cmsMenuService;

    @Value("${server.servlet.context-path}")
    protected String contextPath;

    private List<MenuItem> menuItems = null;

    public List<MenuItem> getSidebarMenuList() {

        if (menuItems == null) {
            this.menuItems = getMenuItemsFromDatabase();
        }

        logger.info("uri: {}", httpServletRequest.getRequestURI());

        this.menuItems.forEach(this::setActiveFalse);

        this.menuItems.forEach(menuItem -> {
            if (checkIfActiveRoute(menuItem, httpServletRequest.getRequestURI())) {
                menuItem.setActive(true);
            }
        });

        logger.info("menuItems: {}", new Gson().toJson(menuItems));

        return menuItems;
    }

    private void setActiveFalse(MenuItem menuItem) {
        menuItem.setActive(false);
        menuItem.getChildren().forEach(this::setActiveFalse);
    }

    private boolean checkIfActiveRoute(MenuItem menuItem, String uri) {

        if (StringUtils.isNotBlank(menuItem.getUrl()) && uri.startsWith(menuItem.getUrl())) {
            return true;
        }

        for (MenuItem child : menuItem.getChildren()) {
            if (checkIfActiveRoute(child, uri)) {
                child.setActive(true);
                return true;
            }
        }

        return false;
    }

    private List<MenuItem> getMenuItemsFromDatabase() {

        return cmsMenuService.findByParentIdNullAndIsDeleteFalse()
                .stream()
                .map(cmsMenu -> new MenuItem(cmsMenu, findChildrenMenuItems(cmsMenu.getId()), contextPath))
                .collect(Collectors.toList());
    }

    private List<MenuItem> findChildrenMenuItems(Long parentId) {

        return cmsMenuService.findByParentIdAndIsDeleteFalse(parentId)
                .stream()
                .map(child -> new MenuItem(child, findChildrenMenuItems(child.getId()), contextPath))
                .collect(Collectors.toList());
    }
}
