package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.form.SystemParameterForm;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.pojo.SystemParameter;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.SystemParameterRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.response.vo.row.SystemParameterRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.SystemParameterDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.SystemParameterPageService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SystemParameterPageServiceImpl extends BasePageServiceImpl implements SystemParameterPageService {

    @Autowired
    private SystemParameterDataTableServiceImpl systemParameterDataTablesService;

    @Autowired
    private SystemParameterRepository systemParameterRepository;

    private Map<String, Field> fieldMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        this.fieldMap = ReflectionUtil.getFieldMap(SystemParameter.class);
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
    public Map<String, Field> getFieldMap() {
        return fieldMap;
    }

    @Override
    public void updateFieldMapValues(Map<String, Field> fieldMap, SystemParameterForm form) {

        fieldMap.get("category").setValue(form.getCategory());
        fieldMap.get("key").setValue(form.getKey());
        fieldMap.get("value").setValue(form.getValue());
        fieldMap.get("description").setValue(form.getDescription());
        fieldMap.get("dataType").setValue(form.getDataType().toString());
    }

    @Override
    public Map<String, Field> getFieldMapForEdit(SystemParameter systemParameter) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(fieldMap);

        fieldMapClone.get("category").setDisabled(true);
        fieldMapClone.get("key").setDisabled(true);
        fieldMapClone.get("dataType").setDisabled(true);

        return fieldMapClone;
    }

    @Override
    public SystemParameter edit(SystemParameter systemParameter, SystemParameterForm form) {

        systemParameter.setValue(form.getValue());
        systemParameter.setDescription(form.getDescription());
        systemParameter = update(systemParameter);

        return systemParameter;
    }

    @Override
    public SystemParameter create(SystemParameterForm form) {

        SystemParameter systemParameter = new SystemParameter();
        systemParameter.setCategory(form.getCategory());
        systemParameter.setKey(form.getKey());
        systemParameter.setValue(form.getValue());
        systemParameter.setDescription(form.getDescription());
        systemParameter.setDataType(form.getDataType());
        systemParameter = create(systemParameter);

        return systemParameter;
    }

}
