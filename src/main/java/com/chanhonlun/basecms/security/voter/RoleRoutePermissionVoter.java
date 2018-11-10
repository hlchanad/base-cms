package com.chanhonlun.basecms.security.voter;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.model.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Collection;

public class RoleRoutePermissionVoter implements AccessDecisionVoter<FilterInvocation> {

    private static final Logger logger = LoggerFactory.getLogger(RoleRoutePermissionVoter.class);

    private final String contextPath;
    private final RequestMatcher assetsMatcher;

    public RoleRoutePermissionVoter(String contextPath, RequestMatcher assetsMatcher) {
        this.contextPath = contextPath;
        this.assetsMatcher = assetsMatcher;
    }

    @Override
    public int vote(Authentication authentication, FilterInvocation filterInvocation, Collection<ConfigAttribute> attributes) {

        if (assetsMatcher.matches(filterInvocation.getHttpRequest())) return ACCESS_ABSTAIN;

        if (!(authentication.getPrincipal() instanceof UserPrincipal)) return ACCESS_ABSTAIN;

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        boolean isSuperAdmin = userPrincipal.getCmsUser().getRoles().stream()
                .anyMatch(role -> MyConstants.CMS_USER_ROLE_SUPER_ADMIN.equals(role.getCode()));

        if (isSuperAdmin) return ACCESS_GRANTED;


        String method   = filterInvocation.getHttpRequest().getMethod();
        String endpoint = filterInvocation.getHttpRequest().getRequestURI().substring(contextPath.length());

        boolean allowed = userPrincipal.getAllowedRoutes()
                .stream()
                .anyMatch(roleRoute -> endpoint.startsWith(roleRoute.getUrl())
                        && (roleRoute.getMethod() == null || roleRoute.getMethod().matches(method)));

        return allowed ? ACCESS_GRANTED : ACCESS_DENIED;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }
}
