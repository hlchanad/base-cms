package com.chanhonlun.basecms.service.datatable;

import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.vo.row.BaseRowVO;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Map;

public interface BaseDataTableService<
        Pojo,
        PK extends Serializable,
        PojoVO extends BaseRowVO,
        Req extends BaseDataTableInput,
        RspVO extends BaseDataTableConfig> {

    void setSection(String section);

    /**
     * Get the instance of DataTablesRepository for handling datatables.js request
     *
     * @return DataTablesRepository for handling datatables.js request
     */
    DataTablesRepository<Pojo, PK> getDataTablesRepository();

    /**
     * find data according to {@code input} (of type {@code DataTablesInput}. this may be extended to support more filtering
     *
     * @param input datatables.js request, extendable
     * @return DataTablesOutput<PojoVO> can be directly used by datatables.js  with the PojoVO type data
     */
    default DataTablesOutput<PojoVO> getDataTablesData(Req input) {
        return getDataTablesRepository().findAll(input, null, getPreFilterSpecification(input), this::getTableVOFromPOJO);
    }

    /**
     * get preFilter specification, default isDeleted = 0, can handle more filtering depends on custom extended DataTableInput
     *
     * @param input datatables.js request, extendable
     * @return Specification of Pojo type for filtering Pojo table
     */
    default Specification<Pojo> getPreFilterSpecification(Req input) {
        return (root, query, cb) -> {

            @SuppressWarnings("unchecked")
            Specification<Pojo> where =  (Specification<Pojo>) Specification
                    .where((root1, query1, cb1) -> cb1.equal(root1.get("isDeleted"), false))
                    .and((root1, query1, cb1) -> cb1.greaterThan(root1.get("id"), 0L));

            return where.toPredicate(root, query, cb);
        };
    }

    /**
     * Converter from Pojo to VO for datatables
     *
     * @param pojo data
     * @return VO for datatables
     */
    PojoVO getTableVOFromPOJO(Pojo pojo);

    /**
     * generate config for datatables including columns settings,
     * pass any extra configs to datatables.js setting,
     * and eventually will be send those back to server when querying datatables in {@code input} of {@link #getDataTablesData}
     *
     * @param extraConfigs key-value pairs
     * @return Config for datatables
     */
    RspVO getDataTableConfig(Map<String, String> extraConfigs);
}
