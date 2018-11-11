package com.chanhonlun.basecms.controller.trait;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.form.BaseForm;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCreatePage;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.Serializable;

public interface DefaultControllerHasCreatePage<
        Pojo extends BasePojo<PojoPK>,
        PojoPK extends Serializable,
        Form extends BaseForm> {

    String getSection();

    DefaultServiceHasCreatePage<Pojo, PojoPK, Form> getDefaultPageHasCreate();

    @GetMapping("/create")
    default String create(Model model) {
        model.addAttribute(MyConstants.PAGE_RESPONSE, getDefaultPageHasCreate().getCreatePageConfig());
        return getSection() + "/create";
    }

    @PostMapping("/create")
    default String doCreate(Model model, Form form) {

        FormError formError = getDefaultPageHasCreate().ifCreateError(form);
        if (formError != null) {
            model.addAttribute(MyConstants.PAGE_RESPONSE, getDefaultPageHasCreate().getCreatePageConfig(form, formError));
            return getSection() + "/create";
        }

        Pojo pojo = getDefaultPageHasCreate().create(form);

        return "redirect:/" + getSection() + "/" + pojo.getId() + "/detail";
    }
}
