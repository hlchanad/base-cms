package com.chanhonlun.basecms.interceptor;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.constant.SessionAttributes;
import com.chanhonlun.basecms.response.ErrorPopup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class ErrorPopupInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ErrorPopupInterceptor.class);

    @Autowired
    private HttpSession httpSession;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

        if (modelAndView == null) return;

        ErrorPopup errorPopup = (ErrorPopup) httpSession.getAttribute(SessionAttributes.ERROR_POPUP);
        Boolean justGotError = (Boolean) httpSession.getAttribute(SessionAttributes.JUST_GOT_ERROR);

        if (errorPopup != null
                && !justGotError
                && modelAndView.getViewName() != null
                && !modelAndView.getViewName().startsWith("redirect")) {
            modelAndView.addObject(MyConstants.ERROR_POPUP_RESPONSE, errorPopup);
            httpSession.removeAttribute(SessionAttributes.ERROR_POPUP);
            httpSession.removeAttribute(SessionAttributes.JUST_GOT_ERROR);
        }

        if (justGotError != null && justGotError) {
            httpSession.setAttribute(SessionAttributes.JUST_GOT_ERROR, false);
        }

    }
}
