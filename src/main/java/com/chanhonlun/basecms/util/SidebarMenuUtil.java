package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.model.UserPrincipal;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.response.vo.MenuItem;
import com.chanhonlun.basecms.service.page.CmsMenuPageService;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SidebarMenuUtil {

    private static final Logger logger = LoggerFactory.getLogger(SidebarMenuUtil.class);

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private CmsMenuPageService cmsMenuPageService;

    @Value("${server.servlet.context-path}")
    protected String contextPath;

    private List<CmsMenu> cmsMenus;

    public void updateMenu() {
        this.cmsMenus = cmsMenuPageService.findByIsDeletedFalse();
    }

    public List<MenuItem> getSidebarMenuList() {

        if (cmsMenus == null) {
            updateMenu();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        List<MenuItem> menuItems = getMenuItems(userPrincipal.getCmsUser().getRoles());

        logger.info("uri: {}", httpServletRequest.getRequestURI());

        menuItems.stream()
                .filter(menuItem -> checkIfActiveRoute(menuItem, httpServletRequest.getRequestURI()))
                .forEach(menuItem -> menuItem.setActive(true));

        logger.info("menuItems: {}", new Gson().toJson(menuItems));

        return menuItems;
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

    private List<MenuItem> getMenuItems(List<Role> roles) {

        boolean isSuperAdmin = roles.stream()
                .map(Role::getCode)
                .anyMatch(MyConstants.CMS_USER_ROLE_SUPER_ADMIN::equals);

        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());

        return cmsMenus.stream()
                .filter(cmsMenu -> cmsMenu.getParentId() == null)
                .filter(cmsMenu -> {
                    List<Long> allowedRoleIds = cmsMenuPageService.getAllowedRoleIds(cmsMenu);
                    return isSuperAdmin || roleIds.stream().anyMatch(allowedRoleIds::contains);
                })
                .map(cmsMenu -> new MenuItem(cmsMenu, findChildrenMenuItems(roles, cmsMenu.getId()), contextPath))
                .collect(Collectors.toList());
    }

    private List<MenuItem> findChildrenMenuItems(List<Role> roles, Long parentId) {

        boolean isSuperAdmin = roles.stream()
                .map(Role::getCode)
                .anyMatch(MyConstants.CMS_USER_ROLE_SUPER_ADMIN::equals);

        List<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());

        return cmsMenus.stream()
                .filter(cmsMenu -> cmsMenu.getParentId() != null)
                .filter(cmsMenu -> cmsMenu.getParentId().equals(parentId))
                .filter(cmsMenu -> {
                    List<Long> allowedRoleIds = cmsMenuPageService.getAllowedRoleIds(cmsMenu);
                    return isSuperAdmin || roleIds.stream().anyMatch(allowedRoleIds::contains);
                })
                .map(child -> new MenuItem(child, findChildrenMenuItems(roles, child.getId()), contextPath))
                .collect(Collectors.toList());
    }
}
