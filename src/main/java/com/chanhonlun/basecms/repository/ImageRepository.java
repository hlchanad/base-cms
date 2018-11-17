package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.Image;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends BaseRepository<Image, Long> {

    Image findByFileNameAndIsDeletedFalse(String fileName);
}
