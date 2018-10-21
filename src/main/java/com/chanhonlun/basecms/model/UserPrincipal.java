package com.chanhonlun.basecms.model;

import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.pojo.RoleRoute;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserPrincipal implements UserDetails {

    private CmsUser cmsUser;

    private List<UserAuthority> authorities;

    private List<RoleRoute> allowedRoutes;

    public UserPrincipal(CmsUser cmsUser, List<Role> roles, List<RoleRoute> allowedRoutes) {
        this.cmsUser = cmsUser;
        this.authorities = roles.stream().map(UserAuthority::new).collect(Collectors.toList());
        this.allowedRoutes = allowedRoutes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return cmsUser.getPassword();
    }

    @Override
    public String getUsername() {
        return cmsUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
