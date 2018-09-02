package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.CmsMenu;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsMenuRepository extends DataTablesRepository<CmsMenu, Long> {

    CmsMenu findByIdAndIsDeleteFalse(Long id);
}
