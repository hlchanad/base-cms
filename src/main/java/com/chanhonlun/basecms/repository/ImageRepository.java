package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends BaseRepository<Image, Long> {

    Image findByFileNameAndIsDeletedFalse(String fileName);

    Page<Image> findByIsDeletedFalse(Pageable pegeable);
}
