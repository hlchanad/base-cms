package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.constant.FieldType;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.FieldOption;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.SystemParameterRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.SystemParameterDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.SystemParameterService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.chanhonlun.basecms.response.vo.row.SystemParameterRowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SystemParameterServiceImpl extends BaseServiceImpl implements SystemParameterService {

    @Autowired
    private SystemParameterDataTableServiceImpl systemParameterDataTablesService;

    @Autowired
    private SystemParameterRepository systemParameterRepository;


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

        List<Field> fields = Arrays.asList(
                Field.builder()
                        .id("category")
                        .title("Category")
                        .type(FieldType.TEXT)
                        .value("GENERAL")
                        .build(),
                Field.builder()
                        .id("key")
                        .title("Key")
                        .type(FieldType.TEXT)
                        .placeholder("key")
                        .build(),
                Field.builder()
                        .id("value")
                        .title("Value")
                        .type(FieldType.TEXT)
                        .build(),

                // --- Test -------------------------------

                Field.builder()
                        .id("password")
                        .title("Password")
                        .type(FieldType.PASSWORD)
                        .hintTitle("Please fulfill the following password complexity criteria")
                        .hintDetail("At least 8 characters with one alphabet, one numeric and one symbol")
                        .build(),
                Field.builder()
                        .id("number")
                        .title("Number")
                        .type(FieldType.NUMBER)
                        .build(),
                Field.builder()
                        .id("date")
                        .title("Date")
                        .type(FieldType.DATE)
                        .build(),
                Field.builder()
                        .id("dateTime")
                        .title("DateTime")
                        .type(FieldType.DATE_TIME)
                        .build(),
                Field.builder()
                        .id("time")
                        .title("TIME")
                        .type(FieldType.TIME)
                        .build(),
                Field.builder()
                        .id("longText")
                        .title("LONG_TEXT")
                        .type(FieldType.LONG_TEXT)
                        .placeholder("textarea placeholder")
                        .value("textarea value")
                        .build(),
                Field.builder()
                        .id("html")
                        .title("HTML")
                        .type(FieldType.HTML)
                        .build(),
                Field.builder()
                        .id("radio")
                        .title("RADIO")
                        .type(FieldType.RADIO)
                        .value("female")
                        .options(Arrays.asList(
                                FieldOption.builder()
                                        .id("male")
                                        .title("Male")
                                        .value("male")
                                        .build(),
                                FieldOption.builder()
                                        .id("female")
                                        .title("Female")
                                        .value("female")
                                        .build()
                        ))
                        .build(),
                Field.builder()
                        .id("dropdown")
                        .title("DROPDOWN")
                        .type(FieldType.DROPDOWN)
                        .value("female")
                        .options(Arrays.asList(
                                FieldOption.builder()
                                        .title("Male")
                                        .value("male")
                                        .build(),
                                FieldOption.builder()
                                        .title("Female")
                                        .value("female")
                                        .build()
                        ))
                        .build(),
                Field.builder()
                        .id("image")
                        .title("Image")
                        .type(FieldType.IMAGE)
                        .build()
        );

        return BaseCreatePageConfig.builder()
                .pageTitle("System Parameter")
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .menu(sidebarMenuUtil.getSidebarMenuList())
                .fields(fields)
                .build();
    }
}
