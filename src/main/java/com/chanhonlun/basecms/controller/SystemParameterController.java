package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.req.datatables.SystemParameterListDataTablesInput;
import com.chanhonlun.basecms.service.SystemParameterService;
import com.chanhonlun.basecms.vo.SystemParameterTableVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/system-parameter")
public class SystemParameterController extends BaseController {

    @Autowired
    private SystemParameterService systemParameterService;

    @GetMapping("/data")
    @ResponseBody
    public DataTablesOutput<SystemParameterTableVO> datatableData(SystemParameterListDataTablesInput input) {
        return systemParameterService.dataTableAPI(input);
    }

    @GetMapping("")
    public String redirectList(Model model) {
        return "redirect:/system-parameter/list";
    }

    @GetMapping("/list")
    public String list(Map<String, Object> model) {
        model.put("CMS_RSP", systemParameterService.getListConfig());
        return "system-parameter/datatable";
    }

    @DeleteMapping("/{id}/delete")
    @ResponseBody
    public ResponseEntity delete(@PathVariable(name = "id") Long id) {

        SystemParameter systemParameter = systemParameterService.findByIdAndIsDeleteFalse(id);

        if (systemParameter == null) {
            return ResponseEntity.notFound().build();
        }

        systemParameterService.softDelete(systemParameter);

        return ResponseEntity.ok().build();
    }
}
