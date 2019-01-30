package com.chanhonlun.basecms.service.data.impl;

import com.chanhonlun.basecms.constant.Status;
import com.chanhonlun.basecms.pojo.RoleRoute;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.RoleRouteRepository;
import com.chanhonlun.basecms.service.data.RoleRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleRouteServiceImpl extends BaseServiceImpl implements RoleRouteService {

    @Autowired
    private RoleRouteRepository roleRouteRepository;

    @Override
    public BaseRepository<RoleRoute, Long> getRepository() {
        return roleRouteRepository;
    }

    @Override
    public List<RoleRoute> findByRoleIdAndIsDeletedFalse(Long id) {
        return roleRouteRepository.findByRoleIdAndIsDeletedFalse(id);
    }

    @Override
    public List<RoleRoute> batchCreate(Long roleId, List<String> routes) {
        return routes.stream()
                .map(route -> {
                    RoleRoute roleRoute = new RoleRoute();
                    roleRoute.setRoleId(roleId);
                    roleRoute.setMethod(null);
                    roleRoute.setUrl(route);
                    roleRoute.setStatus(Status.NORMAL);
                    return create(roleRoute);
                })
                .collect(Collectors.toList());
    }
}
