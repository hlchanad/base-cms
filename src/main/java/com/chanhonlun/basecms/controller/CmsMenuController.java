package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.constant.CommonErrorPopup;
import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasCreatePage;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDataTable;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDetailPage;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasEditPage;
import com.chanhonlun.basecms.form.CmsMenuForm;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.response.vo.row.CmsMenuRowVO;
import com.chanhonlun.basecms.service.page.BasePageService;
import com.chanhonlun.basecms.service.page.CmsMenuPageService;
import com.chanhonlun.basecms.service.trait.*;
import com.chanhonlun.basecms.util.ErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cms-menu")
public class CmsMenuController extends BaseController implements
        DefaultControllerHasDataTable<CmsMenu, Long, CmsMenuRowVO>,
//        DefaultControllerHasDeleteActionButton<CmsMenu, Long>,
        DefaultControllerHasCreatePage<CmsMenu, Long, CmsMenuForm>,
        DefaultControllerHasEditPage<CmsMenu, Long, CmsMenuForm>,
        DefaultControllerHasDetailPage<CmsMenu, Long> {

    @Autowired
    private CmsMenuPageService cmsMenuPageService;

    @Override
    protected BasePageService getPageService() {
        return cmsMenuPageService;
    }

    @Override
    public DefaultServiceHasDataTable<CmsMenu, Long, CmsMenuRowVO> getDefaultPageHasDataTable() {
        return cmsMenuPageService;
    }

    @Override
    public DefaultServiceHasCRUD<CmsMenu, Long> getDefaultPageHasCRUD() {
        return cmsMenuPageService;
    }

    @Override
    public DefaultServiceHasEditPage<CmsMenu, Long, CmsMenuForm> getDefaultPageHasEdit() {
        return cmsMenuPageService;
    }

    @Override
    public DefaultServiceHasDetailPage<CmsMenu, Long> getDefaultPageHasDetail() {
        return cmsMenuPageService;
    }

    @Override
    public DefaultServiceHasCreatePage<CmsMenu, Long, CmsMenuForm> getDefaultPageHasCreate() {
        return cmsMenuPageService;
    }

    @Override
    @PostMapping("/create")
    public String doCreate(Model model, CmsMenuForm form) {

        FormError formError = getDefaultPageHasCreate().ifCreateError(form);
        if (formError != null) {
            model.addAttribute(MyConstants.PAGE_RESPONSE, getDefaultPageHasCreate().getCreatePageConfig(form, formError));
            return getSection() + "/create";
        }

        CmsMenu cmsMenu = getDefaultPageHasCreate().create(form);

        cmsMenuPageService.refreshSidebarMenu();

        return "redirect:/" + getSection() + "/" + cmsMenu.getId() + "/detail";
    }

    @Override
    @PutMapping(value = "/{id}/edit")
    public String doEdit(Model model, @PathVariable(value = "id") Long id, CmsMenuForm form) {

        CmsMenu cmsMenu = getDefaultPageHasCRUD().findByIdAndIsDeletedFalse(id);

        if (cmsMenu == null) {
            ErrorUtil.setError(getHttpSession(), CommonErrorPopup.ERROR_404_0001_RECORD_NOT_FOUND);
            return "redirect:/" + getSection();
        }

        FormError formError = getDefaultPageHasEdit().ifEditError(cmsMenu, form);
        if (formError != null) {
            model.addAttribute(MyConstants.PAGE_RESPONSE, getDefaultPageHasEdit().getEditPageConfig(cmsMenu, form, formError));
            return getSection() + "/edit";
        }

        getDefaultPageHasEdit().edit(cmsMenu, form);

        cmsMenuPageService.refreshSidebarMenu();

        return "redirect:/" + getSection() + "/" + id + "/detail";
    }

    @DeleteMapping("/{id}/delete")
    @ResponseBody
    public ResponseEntity delete(@PathVariable(name = "id") Long id) {

        CmsMenu cmsMenu = getDefaultPageHasCRUD().findByIdAndIsDeletedFalse(id);

        if (cmsMenu == null) {
            return ResponseEntity.notFound().build();
        }

        getDefaultPageHasCRUD().softDelete(cmsMenu);

        cmsMenuPageService.refreshSidebarMenu();

        return ResponseEntity.ok().build();
    }
}
