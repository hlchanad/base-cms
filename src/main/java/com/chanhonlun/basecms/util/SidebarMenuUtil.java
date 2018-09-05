package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.model.MenuItem;
import com.chanhonlun.basecms.service.CmsMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SidebarMenuUtil {

    private static final Logger logger = LoggerFactory.getLogger(SidebarMenuUtil.class);

    @Autowired
    private CmsMenuService cmsMenuService;

    @Value("${server.servlet.context-path}")
    protected String contextPath;

    public List<MenuItem> getSidebarMenuList() {

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
