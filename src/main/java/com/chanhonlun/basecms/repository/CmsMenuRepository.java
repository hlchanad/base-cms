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

    List<CmsMenu> findByParentIdNullAndIsDeletedFalse(Sort sort);

    List<CmsMenu> findByParentIdNotNullAndIsDeletedFalse(Sort sort);

    List<CmsMenu> findByParentIdAndIsDeletedFalse(Long parentId, Sort sort);

    List<CmsMenu> findByIsDeletedFalse();

    List<CmsMenu> findByIdNotAndIsDeletedFalse(Long id);
}
