package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.controller.trait.DefaultControllerHasCreatePage;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDataTable;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasDetailPage;
import com.chanhonlun.basecms.controller.trait.DefaultControllerHasEditPage;
import com.chanhonlun.basecms.form.CmsUserForm;
import com.chanhonlun.basecms.model.UserPrincipal;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.response.vo.row.CmsUserRowVO;
import com.chanhonlun.basecms.service.page.BasePageService;
import com.chanhonlun.basecms.service.page.CmsUserPageService;
import com.chanhonlun.basecms.service.trait.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/cms-user")
public class CmsUserController extends BaseController implements
        DefaultControllerHasDataTable<CmsUser, Long, CmsUserRowVO>,
//        DefaultControllerHasDeleteActionButton<CmsUser, Long>,
        DefaultControllerHasCreatePage<CmsUser, Long, CmsUserForm>,
        DefaultControllerHasEditPage<CmsUser, Long, CmsUserForm>,
        DefaultControllerHasDetailPage<CmsUser, Long> {

    @Autowired
    private CmsUserPageService cmsUserPageService;

    @Override
    protected BasePageService getPageService() {
        return cmsUserPageService;
    }

    @Override
    public DefaultServiceHasDataTable<CmsUser, Long, CmsUserRowVO> getDefaultPageHasDataTable() {
        return cmsUserPageService;
    }

    @Override
    public DefaultServiceHasCRUD<CmsUser, Long> getDefaultPageHasCRUD() {
        return cmsUserPageService;
    }

    @Override
    public DefaultServiceHasEditPage<CmsUser, Long, CmsUserForm> getDefaultPageHasEdit() {
        return cmsUserPageService;
    }

    @Override
    public DefaultServiceHasDetailPage<CmsUser, Long> getDefaultPageHasDetail() {
        return cmsUserPageService;
    }

    @Override
    public DefaultServiceHasCreatePage<CmsUser, Long, CmsUserForm> getDefaultPageHasCreate() {
        return cmsUserPageService;
    }

    @DeleteMapping("/{id}/delete")
    @ResponseBody
    public ResponseEntity delete(@PathVariable(name = "id") Long id) {

        CmsUser cmsUser = getDefaultPageHasCRUD().findByIdAndIsDeletedFalse(id);

        if (cmsUser == null) {
            return ResponseEntity.notFound().build();
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        if (userPrincipal.getCmsUser().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        getDefaultPageHasCRUD().softDelete(cmsUser);

        return ResponseEntity.ok().build();
    }
}
