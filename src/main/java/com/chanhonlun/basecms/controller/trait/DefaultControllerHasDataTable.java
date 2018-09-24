package com.chanhonlun.basecms.controller.trait;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.request.datatable.DefaultDataTableInput;
import com.chanhonlun.basecms.response.vo.row.BaseRowVO;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

public interface DefaultControllerHasDataTable<
        Pojo,
        PK extends Serializable,
        PojoVO extends BaseRowVO> {

    DefaultServiceHasDataTable<Pojo, PK, PojoVO> getDefaultPageHasDataTable();

    String getSection();

    @GetMapping("/data")
    @ResponseBody
    default DataTablesOutput<PojoVO> datatableData(DefaultDataTableInput input) {
        return getDefaultPageHasDataTable().dataTableAPI(input);
    }

    @GetMapping("")
    default String redirectList() {
        return "redirect:/" + getSection() + "/list";
    }

    @GetMapping("/list")
    default String list(Model model) {
        model.addAttribute(MyConstants.PAGE_RESPONSE, getDefaultPageHasDataTable().getListPageConfig());
        return getSection() + "/datatable";
    }

}
