package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.Role;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface RoleRepository extends
        BaseRepository<Role, Long>,
        DataTablesRepository<Role, Long> {

    Role findByCodeAndIsDeleteFalse(String code);
}
