package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.Role;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import java.util.List;

public interface RoleRepository extends
        BaseRepository<Role, Long>,
        DataTablesRepository<Role, Long> {

    Role findByCodeAndIsDeleteFalse(String code);

    List<Role> findByIsDeleteFalse();
}
