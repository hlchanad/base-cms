package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.RoleRoute;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRouteRepository extends BaseRepository<RoleRoute, Long> {

    List<RoleRoute> findByRoleIdAndIsDeleteFalse(Long id);
}
