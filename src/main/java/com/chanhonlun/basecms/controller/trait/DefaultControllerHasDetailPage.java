package com.chanhonlun.basecms.controller.trait;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDetailPage;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.Serializable;

public interface DefaultControllerHasDetailPage<
        Pojo extends BasePojo<PojoPK>,
        PojoPK extends Serializable> {

    String getSection();

    DefaultServiceHasDetailPage<Pojo, PojoPK> getDefaultPageHasDetail();

    @GetMapping("/{id}")
    default String redirectDetail(@PathVariable(value = "id") Long id) {
        return "redirect:/" + getSection() + "/" + id + "/detail";
    }

    @GetMapping("/{id}/detail")
    default String detail(Model model, @PathVariable(value = "id") PojoPK id) {
        model.addAttribute(MyConstants.PAGE_RESPONSE, getDefaultPageHasDetail().getDetailPageConfig(id));
        return getSection() + "/detail";
    }
}
