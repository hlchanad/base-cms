package com.chanhonlun.basecms.form;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class CmsUserForm extends BaseForm {

    private String username;

    private String password;

    private String email;

    private List<Long> userRoles;
}
