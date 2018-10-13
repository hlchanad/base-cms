package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.*;
import com.chanhonlun.basecms.form.SystemParameterForm;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.response.vo.row.SystemParameterRowVO;
import com.chanhonlun.basecms.service.page.BasePageService;
import com.chanhonlun.basecms.service.page.SystemParameterPageService;
import com.chanhonlun.basecms.service.trait.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system-parameter")
public class SystemParameterController extends BaseController implements
        DefaultControllerHasDataTable<SystemParameter, Long, SystemParameterRowVO>,
        DefaultControllerHasDeleteActionButton<SystemParameter, Long>,
        DefaultControllerHasCreatePage<SystemParameter, Long, SystemParameterForm>,
        DefaultControllerHasEditPage<SystemParameter, Long, SystemParameterForm>,
        DefaultControllerHasDetailPage<SystemParameter, Long> {

    @Autowired
    private SystemParameterPageService systemParameterPageService;

    @Override
    protected BasePageService getPageService() {
        return systemParameterPageService;
    }

    @Override
    public DefaultServiceHasCRUD<SystemParameter, Long> getDefaultPageHasCRUD() {
        return systemParameterPageService;
    }

    @Override
    public DefaultServiceHasDataTable<SystemParameter, Long, SystemParameterRowVO> getDefaultPageHasDataTable() {
        return systemParameterPageService;
    }

    @Override
    public DefaultServiceHasEditPage<SystemParameter, Long, SystemParameterForm> getDefaultPageHasEdit() {
        return systemParameterPageService;
    }

    @Override
    public DefaultServiceHasDetailPage<SystemParameter, Long> getDefaultPageHasDetail() {
        return systemParameterPageService;
    }

    @Override
    public DefaultServiceHasCreatePage<SystemParameter, Long, SystemParameterForm> getDefaultPageHasCreate() {
        return systemParameterPageService;
    }
}
