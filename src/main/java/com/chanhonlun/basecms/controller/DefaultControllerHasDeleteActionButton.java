package com.chanhonlun.basecms.controller;

import com.chanhonlun.basecms.pojo.BasePojo;
import com.chanhonlun.basecms.service.DefaultServiceHasCRUD;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

public interface DefaultControllerHasDeleteActionButton<Pojo extends BasePojo<PK>, PK extends Serializable> {

    DefaultServiceHasCRUD<Pojo, PK> getDefaultPageHasCRUD();

    @DeleteMapping("/{id}/delete")
    @ResponseBody
    default ResponseEntity delete(@PathVariable(name = "id") PK id) {

        Pojo pojo = getDefaultPageHasCRUD().findByIdAndIsDeleteFalse(id);

        if (pojo == null) {
            return ResponseEntity.notFound().build();
        }

        getDefaultPageHasCRUD().softDelete(pojo);

        return ResponseEntity.ok().build();
    }
}
