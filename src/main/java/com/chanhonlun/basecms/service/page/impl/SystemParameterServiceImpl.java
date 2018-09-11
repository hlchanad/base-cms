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
import com.chanhonlun.basecms.response.vo.row.SystemParameterRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.SystemParameterDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.SystemParameterService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.google.common.base.CaseFormat;
import com.mysema.codegen.StringUtils;
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
                .map(property -> {
                    Field.FieldBuilder fieldBuilder = Field.builder();

                    // --- id --------------
                    fieldBuilder.id(property.getName());

                    // --- title ------------
                    String lowerUnderscored = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, property.getName());
                    String lowerSpaced      = lowerUnderscored.replaceAll("_", " ");
                    fieldBuilder.title(StringUtils.capitalize(lowerSpaced));


                    // --- type -------------
                    Class<?> clazz = property.getType();
                    if (Date.class.equals(clazz)) {
                        fieldBuilder.type(FieldType.DATE);
                    }
                    else if (Short.class.equals(clazz) || Integer.class.equals(clazz) || Long.class.equals(clazz)) {
                        fieldBuilder.type(FieldType.NUMBER);
                    }
                    else if (Float.class.equals(clazz) || Double.class.equals(clazz)) {
                        fieldBuilder.type(FieldType.NUMBER);
                        fieldBuilder.numberStep(0.1f);
                    }
                    else if (clazz.isEnum()) {
                        @SuppressWarnings("unchecked")
                        List<? extends Enum> enums = (List<? extends Enum>) Arrays.asList(clazz.getEnumConstants());

                        fieldBuilder.type(enums.size() > 5 ? FieldType.DROPDOWN : FieldType.RADIO);

                        List<FieldOption> fieldOptions = enums.stream().map((Enum anEnum) ->
                                FieldOption.builder()
                                        .id(property.getName() + "_" + anEnum.name().toLowerCase())
                                        .title(StringUtils.capitalize(anEnum.name().replaceAll("_", " ").toLowerCase()))
                                        .value(anEnum.name())
                                        .build())
                                .collect(Collectors.toList());

                        fieldBuilder.options(fieldOptions);
                    }
                    else {
                        fieldBuilder.type(FieldType.TEXT);
                    }

                    return fieldBuilder.build();
                })
                .forEach(field -> fieldMap.put(field.getId(), field));
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

        return BaseCreatePageConfig.builder()
                .pageTitle("System Parameter")
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .menu(sidebarMenuUtil.getSidebarMenuList())
                .fields(new ArrayList<>(fieldMap.values()))
                .build();
    }
}
