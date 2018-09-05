package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.SystemParameter;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemParameterRepository extends
        DataTablesRepository<SystemParameter, Long>,
        BaseRepository<SystemParameter, Long> {

}
