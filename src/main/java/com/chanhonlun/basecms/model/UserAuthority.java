package com.chanhonlun.basecms.model;

import com.chanhonlun.basecms.pojo.Role;
import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

    private Role role;

    public UserAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.getCode();
    }
}
