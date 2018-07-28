package com.chanhonlun.springboottest.repository;

import com.chanhonlun.springboottest.pojo.SystemParameter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemParameterRepository extends CrudRepository<SystemParameter, Long> {

    List<SystemParameter> findByIsDeleteFalse();

    SystemParameter findByIdAndIsDeleteFalse(Long id);
}
