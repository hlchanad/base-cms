package com.chanhonlun.basecms.service;


import com.chanhonlun.basecms.model.BaseConfig;

public interface DashboardService extends BaseService {

    BaseConfig getPageConfig();
}
