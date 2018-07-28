package com.chanhonlun.springboottest.repository;

import com.chanhonlun.springboottest.pojo.Test;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Test, Long> {
}
