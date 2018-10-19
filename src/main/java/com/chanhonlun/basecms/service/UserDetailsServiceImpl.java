package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.model.UserPrincipal;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.repository.CmsUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private CmsUserRepository cmsUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CmsUser cmsUser = cmsUserRepository.findByUsernameAndIsDeleteFalse(username);

        if (cmsUser == null) return null;

        return new UserPrincipal(cmsUser, cmsUser.getRoles());
    }
}
