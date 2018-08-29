package com.chanhonlun.springboottest.controller;

import com.chanhonlun.springboottest.pojo.SystemParameter;
import com.chanhonlun.springboottest.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.springboottest.service.SystemParamterService;
import com.chanhonlun.springboottest.vo.SystemParameterTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
@Controller
@RequestMapping("/system-parameter")
public class SystemParameterController extends BaseController {

    @Autowired
    private SystemParamterService systemParamterService;

    @GetMapping("")
    @ResponseBody
    public DataTablesOutput<SystemParameterTableVO> datatableData(@Valid SystemParameterListDataTablesInput input) {
        return systemParamterService.systemParameterDataTablesAPI(input);
    }

    @GetMapping("/list")
    public String list(Map<String, Object> model) {
        model.put("CMS_RSP", systemParamterService.getListConfig());
        return "common/datatable";
    }

    @DeleteMapping("/{id}/delete")
    @ResponseBody
    public ResponseEntity delete(@PathVariable(name = "id") Long id) {

        SystemParameter systemParameter = systemParamterService.findByIdAndIsDeleteFalse(id);

        if (systemParameter == null) {
            return ResponseEntity.notFound().build();
        }

        systemParamterService.softDelete(systemParameter);

        return ResponseEntity.ok().build();
    }
}
