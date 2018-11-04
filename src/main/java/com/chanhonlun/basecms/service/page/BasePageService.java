package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.model.UserPrincipal;

public interface BasePageService {

    void setSection(String section);

    String getSection();

    String getPageTitle();

    String getContextPath();

    UserPrincipal getCurrentUser();
}
