package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.Image;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends BaseRepository<Image, Long> {

    Image findByFileNameAndIsDeletedFalse(String fileName);

    List<Image> findByIsDeletedFalse(Pageable pegable);
}
