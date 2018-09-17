package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.constant.FieldType;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.SystemParameterRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.FieldOption;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.page.DefaultCreatePageConfig;
import com.chanhonlun.basecms.response.vo.row.SystemParameterRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.SystemParameterDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.SystemParameterService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.google.common.base.CaseFormat;
import com.mysema.codegen.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SystemParameterServiceImpl extends BaseServiceImpl implements SystemParameterService {

    @Autowired
    private SystemParameterDataTableServiceImpl systemParameterDataTablesService;

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    private Map<String, Field> fieldMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        ReflectionUtil.getPojoFields(SystemParameter.class)
                .stream()
                .map(property -> new ImmutablePair<>(property.getName(), ReflectionUtil.getFieldFromProperty(property)))
                .forEach(pair -> fieldMap.put(pair.getKey(), pair.getValue()));
    }

    @Override
    public BaseRepository<SystemParameter, Long> getRepository() {
        return systemParameterRepository;
    }

    @Override
    public BaseDataTableService<SystemParameter, Long, SystemParameterRowVO, BaseDataTableInput, BaseDataTableConfig> getDataTablesService() {
        return systemParameterDataTablesService;
    }

    @Override
    public BreadcrumbUtil getBreadcrumbUtil() {
        return breadcrumbUtil;
    }

    @Override
    public SidebarMenuUtil getSidebarMenuUtil() {
        return sidebarMenuUtil;
    }

    @Override
    public BaseCreatePageConfig getCreatePageConfig() {

        return DefaultCreatePageConfig.builder()
                .pageTitle("System Parameter")
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .menu(sidebarMenuUtil.getSidebarMenuList())
                .fields(new ArrayList<>(fieldMap.values()))
                .build();
    }
}
