package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.CmsMenu;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CmsMenuRepository extends
        DataTablesRepository<CmsMenu, Long>,
        BaseRepository<CmsMenu, Long> {

    List<CmsMenu> findByParentIdNullAndIsDeleteFalse(Sort sort);

    List<CmsMenu> findByParentIdNotNullAndIsDeleteFalse(Sort sort);

    List<CmsMenu> findByParentIdAndIsDeleteFalse(Long parentId, Sort sort);
}
