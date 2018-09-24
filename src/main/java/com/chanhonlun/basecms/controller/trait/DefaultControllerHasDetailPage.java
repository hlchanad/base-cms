package com.chanhonlun.basecms.controller.trait;

import com.chanhonlun.basecms.constant.CommonErrorPopup;
import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDetailPage;
import com.chanhonlun.basecms.util.ErrorUtil;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

public interface DefaultControllerHasDetailPage<
        Pojo extends BasePojo<PojoPK>,
        PojoPK extends Serializable> {

    String getSection();

    HttpSession getHttpSession();

    DefaultServiceHasCRUD<Pojo, PojoPK> getDefaultPageHasCRUD();

    DefaultServiceHasDetailPage<Pojo, PojoPK> getDefaultPageHasDetail();

    @GetMapping("/{id}")
    default String redirectDetail(@PathVariable(value = "id") PojoPK id) {
        return "redirect:/" + getSection() + "/" + id + "/detail";
    }

    @GetMapping("/{id}/detail")
    default String detail(Model model, @PathVariable(value = "id") PojoPK id) {

        Pojo pojo = getDefaultPageHasCRUD().findByIdAndIsDeleteFalse(id);

        if (pojo == null) {
            ErrorUtil.setError(getHttpSession(), CommonErrorPopup.ERROR_404_0001_RECORD_NOT_FOUND);
            return "redirect:/" + getSection();
        }

        model.addAttribute(MyConstants.PAGE_RESPONSE, getDefaultPageHasDetail().getDetailPageConfig(id));
        return getSection() + "/detail";
    }
}
