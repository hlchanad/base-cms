package com.chanhonlun.basecms.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BaseConfig {

    private List<Breadcrumb> breadcrumbs;

    private List<MenuItem> menu;
}
