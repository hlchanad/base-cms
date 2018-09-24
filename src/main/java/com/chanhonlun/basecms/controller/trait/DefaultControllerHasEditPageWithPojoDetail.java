package com.chanhonlun.basecms.controller.trait;

import com.chanhonlun.basecms.constant.CommonErrorPopup;
import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.form.BaseForm;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.pojo.BaseDetailPojo;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasEditPageWithPojoDetail;
import com.chanhonlun.basecms.util.ErrorUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

public interface DefaultControllerHasEditPageWithPojoDetail<
        Pojo extends BasePojo<PojoPK>,
        PojoPK extends Serializable,
        PojoDetail extends BaseDetailPojo<PojoDetailPK, PojoPK>,
        PojoDetailPK extends Serializable,
        Form extends BaseForm> {

    String getSection();

    HttpSession getHttpSession();

    DefaultServiceHasCRUD<Pojo, PojoPK> getDefaultPageHasCRUD();

    DefaultServiceHasEditPageWithPojoDetail<Pojo, PojoPK, PojoDetail, PojoDetailPK, Form> getDefaultPageHasEdit();

    @GetMapping(value = "/{id}/edit")
    default String edit(Model model, @PathVariable(value = "id") PojoPK id) {

        Pojo pojo = getDefaultPageHasCRUD().findByIdAndIsDeleteFalse(id);

        if (pojo == null) {
            ErrorUtil.setError(getHttpSession(), CommonErrorPopup.ERROR_404_0001_RECORD_NOT_FOUND);
            return "redirect:/" + getSection();
        }

        model.addAttribute(MyConstants.PAGE_RESPONSE, getDefaultPageHasEdit().getEditPageConfig(pojo));
        return getSection() + "/edit";
    }

    @PutMapping(value = "/{id}/edit")
    default String doEdit(Model model, @PathVariable(value = "id") PojoPK id, Form form) {

        Pojo pojo = getDefaultPageHasCRUD().findByIdAndIsDeleteFalse(id);

        if (pojo == null) {
            ErrorUtil.setError(getHttpSession(), CommonErrorPopup.ERROR_404_0001_RECORD_NOT_FOUND);
            return "redirect:/" + getSection();
        }

        FormError formError = getDefaultPageHasEdit().ifEditError(pojo, form);
        if (formError != null) {
            model.addAttribute(MyConstants.PAGE_RESPONSE, getDefaultPageHasEdit().getEditPageConfig(pojo, form, formError));
            return getSection() + "/edit";
        }

        getDefaultPageHasEdit().edit(pojo, form);

        return "redirect:/" + getSection() + "/" + id + "/detail";
    }
}
