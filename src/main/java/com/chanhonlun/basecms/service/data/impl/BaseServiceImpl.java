package com.chanhonlun.basecms.service.data.impl;

import com.chanhonlun.basecms.model.UserPrincipal;
import com.chanhonlun.basecms.service.data.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseServiceImpl implements BaseService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public UserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getPrincipal() instanceof UserPrincipal
                ? (UserPrincipal) authentication.getPrincipal() : null;
    }
}
