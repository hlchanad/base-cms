package com.chanhonlun.basecms.service.datatable;

import com.chanhonlun.basecms.model.component.BaseDataTableConfig;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.vo.row.BaseRowVO;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDataTableService<
        Pojo,
        PK extends Serializable,
        PojoVO extends BaseRowVO,
        Req extends BaseDataTableInput,
        RspVO extends BaseDataTableConfig> {

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
     * get preFilter specification, default isDelete = 0, can handle more filtering depends on custom extended DataTableInput
     *
     * @param input datatables.js request, extendable
     * @return Specification of Pojo type for filtering Pojo table
     */
    default Specification<Pojo> getPreFilterSpecification(Req input) {
        return (root, query, cb) -> {

            Predicate predicate = cb.conjunction();
            List<Expression<Boolean>> expressions = predicate.getExpressions();

            expressions.add(cb.equal(root.get("isDelete"), false));

            return predicate;
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
    RspVO getDataTablesConfig(Map<String, String> extraConfigs);
}
