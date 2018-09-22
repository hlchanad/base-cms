package com.chanhonlun.basecms.controller.trait;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.pojo.BaseDetailPojo;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDetailPageWithPojoDetail;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

public interface DefaultControllerHasDetailPageWithPojoDetail<
        Pojo extends BasePojo<PojoPK>,
        PojoPK extends Serializable,
        PojoDetail extends BaseDetailPojo<PojoDetailPK, PojoPK>,
        PojoDetailPK extends Serializable> {

    String getSection();

    DefaultServiceHasCRUD<Pojo, PojoPK> getDefaultPageHasCRUD();

    DefaultServiceHasDetailPageWithPojoDetail<Pojo, PojoPK, PojoDetail, PojoDetailPK> getDefaultPageHasDetail();

    @GetMapping("/{id}")
    default String redirectDetail(@PathVariable(value = "id") Long id) {
        return "redirect:/" + getSection() + "/" + id + "/detail";
    }

    @GetMapping("/{id}/detail")
    default String detail(Model model, @PathVariable(value = "id") PojoPK id) {

        Pojo pojo = getDefaultPageHasCRUD().findByIdAndIsDeleteFalse(id);

        if (pojo == null) {
            return "redirect:/" + getSection();
        }

        model.addAttribute(MyConstants.PAGE_RESPONSE, getDefaultPageHasDetail().getDetailPageConfig(id));
        return getSection() + "/detail";
    }
}
