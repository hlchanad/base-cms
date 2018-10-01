package com.chanhonlun.basecms.service.datatable;

import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.DataTableColumn;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.component.DefaultDataTableConfig;
import com.chanhonlun.basecms.response.vo.row.BaseRowVO;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface DefaultDataTableService<
        Pojo,
        PK extends Serializable,
        PojoVO extends BaseRowVO>
        extends BaseDataTableService<Pojo, PK, PojoVO, BaseDataTableInput, BaseDataTableConfig> {

    String getSection();

    String getContextPath();

    List<DataTableColumn> getDataTableColumns();

    @Override
    default BaseDataTableConfig getDataTableConfig(Map<String, String> extraConfigs) {
        return DefaultDataTableConfig.builder()
                .title(StringUtils.capitalize(getSection().replace("-", " ")))
                .dataTableId(getSection())
                .ajaxUrl(getContextPath() + "/" + getSection() + "/data")
                .dataTableColumns(getDataTableColumns())
                .extraConfigs(extraConfigs)
                .build();
    }
}
