package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDataTable;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDeleteActionButton;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.service.page.BaseService;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.service.page.SystemParameterService;
import com.chanhonlun.basecms.response.vo.row.SystemParameterRowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system-parameter")
public class SystemParameterController extends BaseController implements
        DefaultControllerHasDataTable<SystemParameter, Long, SystemParameterRowVO>,
        DefaultControllerHasDeleteActionButton<SystemParameter, Long> {

    @Autowired
    private SystemParameterService systemParameterService;

    @Override
    public DefaultServiceHasDataTable<SystemParameter, Long, SystemParameterRowVO> getDefaultPageHasDataTable() {
        return systemParameterService;
    }

    @Override
    public DefaultServiceHasCRUD<SystemParameter, Long> getDefaultPageHasCRUD() {
        return systemParameterService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute(MyConstants.PAGE_RESPONSE, systemParameterService.getCreatePageConfig());
        return "system-parameter/create";
    }

    @Override
    protected BaseService getService() {
        return systemParameterService;
    }
}
