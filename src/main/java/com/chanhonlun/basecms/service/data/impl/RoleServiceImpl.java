package com.chanhonlun.basecms.service.data.impl;

import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.RoleRepository;
import com.chanhonlun.basecms.service.data.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public BaseRepository<Role, Long> getRepository() {
        return roleRepository;
    }

    @Override
    public List<Role> findBySelectableTrueAndIsDeleteFalse() {
        return roleRepository.findBySelectableTrueAndIsDeleteFalse();
    }
}
