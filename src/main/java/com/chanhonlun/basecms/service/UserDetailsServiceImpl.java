package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.model.UserPrincipal;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.pojo.RoleRoute;
import com.chanhonlun.basecms.repository.CmsUserRepository;
import com.chanhonlun.basecms.repository.RoleRouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private CmsUserRepository cmsUserRepository;

    @Autowired
    private RoleRouteRepository roleRouteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CmsUser cmsUser = cmsUserRepository.findByUsernameAndIsDeleteFalse(username);

        if (cmsUser == null) return null;

        List<RoleRoute> roleRoutes = cmsUser.getRoles()
                .stream()
                .map(Role::getId)
                .map(roleRouteRepository::findByRoleIdAndIsDeleteFalse)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return new UserPrincipal(cmsUser, cmsUser.getRoles(), roleRoutes);
    }
}
