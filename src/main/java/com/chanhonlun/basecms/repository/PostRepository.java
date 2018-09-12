package com.chanhonlun.basecms.repository;

import com.chanhonlun.basecms.pojo.Post;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

public interface PostRepository extends
        BaseRepository<Post, Long>,
        DataTablesRepository<Post, Long> {
}
