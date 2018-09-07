package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.CmsUser;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsUserRepository extends
        DataTablesRepository<CmsUser, Long>,
        BaseRepository<CmsUser, Long> {
}
