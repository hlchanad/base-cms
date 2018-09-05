package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.req.datatables.CmsMenuListDataTablesInput;
import com.chanhonlun.basecms.service.CmsMenuService;
import com.chanhonlun.basecms.vo.CmsMenuTableVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/cms-menu")
public class CmsMenuController extends BaseController {

    @Autowired
    private CmsMenuService cmsMenuService;

    @GetMapping("/data")
    @ResponseBody
    public DataTablesOutput<CmsMenuTableVO> datatableData(CmsMenuListDataTablesInput input) {
        return cmsMenuService.dataTableAPI(input);
    }

    @GetMapping("")
    public String redirectList(Model model) {
        return "redirect:/cms-menu/list";
    }

    @GetMapping("/list")
    public String list(Map<String, Object> model) {
        model.put("CMS_RSP", cmsMenuService.getListConfig());
        return "cms-menu/datatable";
    }

    @DeleteMapping("/{id}/delete")
    @ResponseBody
    public ResponseEntity delete(@PathVariable(name = "id") Long id) {

        CmsMenu cmsMenu = cmsMenuService.findByIdAndIsDeleteFalse(id);

        if (cmsMenu == null) {
            return ResponseEntity.notFound().build();
        }

        cmsMenuService.softDelete(cmsMenu);

        return ResponseEntity.ok().build();
    }

}
