package com.chanhonlun.basecms.req.datatables;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

@Data
@EqualsAndHashCode(callSuper = true)
public class CmsMenuListDataTablesInput extends DataTablesInput {
}
