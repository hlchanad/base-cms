package com.chanhonlun.basecms.service.data;

import com.chanhonlun.basecms.pojo.RoleRoute;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;

import java.util.List;

public interface RoleRouteService extends DefaultServiceHasCRUD<RoleRoute, Long> {

    List<RoleRoute> findByRoleIdAndIsDeletedFalse(Long id);

    List<RoleRoute> batchCreate(Long roleId, List<String> routes);
}
