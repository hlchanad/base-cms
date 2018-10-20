package com.chanhonlun.basecms.controller.advice;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.model.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserAdvice {

    @ModelAttribute(MyConstants.CURRENT_USER)
    public UserPrincipal getCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userPrincipal;
    }
}
