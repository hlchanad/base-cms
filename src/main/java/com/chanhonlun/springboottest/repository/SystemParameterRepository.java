package com.chanhonlun.springboottest.repository;

import com.chanhonlun.springboottest.pojo.SystemParameter;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository
public interface SystemParameterRepository extends DataTablesRepository<SystemParameter, Long> {

    List<SystemParameter> findByIsDeleteFalse();

    SystemParameter findByIdAndIsDeleteFalse(Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
            value = "MERGE INTO SYSTEM_PARAMETER t " +
                    "USING DUAL " +
                    "ON (t.CATEGORY = :category " +
                    "  AND t.KEY = :key " +
                    "  AND t.IS_DELETE = 0)" +
                    "WHEN MATCHED THEN " +
                    "  UPDATE SET " +
                    "    t.VALUE                  = :value, " +
                    "    t.DESCRIPTION            = :description, " +
                    "    t.IS_CONFIGURABLE_IN_CMS = :isConfigurableInCms, "+
                    "    t.UPDATED_BY             = :updatedBy, " +
                    "    t.UPDATED_AT             = :updatedAt " +
                    "WHEN NOT MATCHED THEN " +
                    "  INSERT (CATEGORY, KEY, VALUE, DESCRIPTION, DATA_TYPE, IS_CONFIGURABLE_IN_CMS, " +
                    "    STATUS, IS_DELETE, CREATED_BY, CREATED_AT, UPDATED_BY, UPDATED_AT) " +
                    "  VALUES (:category, :key, :value, :description, :dataType, :isConfigurableInCms," +
                    "    :status, :isDelete, :createdBy, :createdAt, :updatedBy, :updatedAt)")
    int upsert(@Param(value = "category")               String  category,
               @Param(value = "key")                    String  key,
               @Param(value = "value")                  String  value,
               @Param(value = "description")            String  description,
               @Param(value = "dataType")               String  dataType,
               @Param(value = "isConfigurableInCms")    Boolean isConfigurableInCms,
               @Param(value = "status")                 String  status,
               @Param(value = "isDelete")               Boolean isDelete,
               @Param(value = "createdBy")              Long    createdBy,
               @Param(value = "createdAt")              Date    createdAt,
               @Param(value = "updatedBy")              Long    updatedBy,
               @Param(value = "updatedAt")              Date    updatedAt);
}
