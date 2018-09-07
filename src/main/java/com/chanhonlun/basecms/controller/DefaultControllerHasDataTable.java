package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.service.DefaultPageHasDataTable;
import com.chanhonlun.basecms.vo.BaseDataTablesVO;
import com.chanhonlun.basecms.vo.BaseTableVO;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

public interface DefaultControllerHasDataTable<
        Pojo,
        PK extends Serializable,
        PojoVO extends BaseTableVO,
        Req extends DataTablesInput,
        RspVO extends BaseDataTablesVO> {

    DefaultPageHasDataTable<Pojo, PK, PojoVO, Req, RspVO> getDefaultPageHasDataTable();

    String getSection();

    @GetMapping("/data")
    @ResponseBody
    default DataTablesOutput<PojoVO> datatableData(Req input) {
        return getDefaultPageHasDataTable().dataTableAPI(input);
    }

    @GetMapping("")
    default String redirectList() {
        return "redirect:/" + getSection() + "/list";
    }

    @GetMapping("/list")
    default String list(Model model) {
        model.addAttribute("CMS_RSP", getDefaultPageHasDataTable().getListConfig());
        return getSection() + "/datatable";
    }

}
