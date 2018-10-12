package com.chanhonlun.basecms.util;

import com.chanhonlun.basecms.constant.SessionAttributes;
import com.chanhonlun.basecms.response.vo.ErrorPopup;

import javax.servlet.http.HttpSession;

public class ErrorUtil {

    public static void setError(HttpSession httpSession, ErrorPopup errorPopup) {
        httpSession.setAttribute(SessionAttributes.ERROR_POPUP, errorPopup);
        httpSession.setAttribute(SessionAttributes.JUST_GOT_ERROR, true);
    }
}
