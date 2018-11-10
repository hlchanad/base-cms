package com.chanhonlun.basecms.service.data;

import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;

import java.util.List;

public interface RoleService extends DefaultServiceHasCRUD<Role, Long> {

    List<Role> findBySelectableTrueAndIsDeletedFalse();
}
