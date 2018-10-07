package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.FieldType;
import com.chanhonlun.basecms.form.CmsUserForm;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.CmsUserRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.vo.row.CmsUserRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.CmsUserDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.CmsUserService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CmsUserServiceImpl extends BaseServiceImpl implements CmsUserService {

    @Autowired
    private CmsUserDataTableServiceImpl cmsUserDataTablesService;

    @Autowired
    private CmsUserRepository cmsUserRepository;

    private Map<String, Field> fieldMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        ReflectionUtil.getClassFields(CmsUser.class)
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .map(property -> new ImmutablePair<>(property.getName(), ReflectionUtil.getFieldFromProperty(property)))
                .forEach(pair -> fieldMap.put(pair.getKey(), pair.getValue()));

        fieldMap.get("password").setType(FieldType.PASSWORD);
        fieldMap.get("email").setType(FieldType.EMAIL);
    }

    @Override
    public BaseRepository<CmsUser, Long> getRepository() {
        return cmsUserRepository;
    }

    @Override
    public BaseDataTableService<CmsUser, Long, CmsUserRowVO, BaseDataTableInput, BaseDataTableConfig> getDataTablesService() {
        return cmsUserDataTablesService;
    }

    @Override
    public String getPageTitle() {
        return "CMS User";
    }

    @Override
    public Map<String, Field> getFieldMap() {
        return fieldMap;
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
    public void updateFieldMapValues(Map<String, Field> fieldMap, CmsUserForm form) {

        fieldMap.get("username").setValue(form.getUsername());
        fieldMap.get("password").setValue(form.getPassword());
        fieldMap.get("email").setValue(form.getEmail());
    }

    @Override
    public CmsUser edit(CmsUser cmsUser, CmsUserForm form) {

        cmsUser.setEmail(form.getEmail());
        cmsUser = update(cmsUser);

        return cmsUser;
    }

    @Override
    public CmsUser create(CmsUserForm form) {

        CmsUser cmsUser = new CmsUser();
        cmsUser.setUsername(form.getUsername());
        cmsUser.setPassword(form.getPassword());
        cmsUser.setEmail(form.getEmail());
        cmsUser = create(cmsUser);

        return cmsUser;
    }

    @Override
    public Map<String, Field> getFieldMapForEdit() {

        Gson gson = new Gson();

        Map<String, Field> fieldMapClone = gson.fromJson(gson.toJson(fieldMap), new TypeToken<Map<String, Field>>(){}.getType());

        fieldMapClone.get("username").setDisabled(true);
        fieldMapClone.get("password").setShow(false);

        return fieldMapClone;
    }

    @Override
    public Map<String, Field> getFieldMapForDetail() {

        Gson gson = new Gson();

        Map<String, Field> fieldMapClone = gson.fromJson(gson.toJson(fieldMap), new TypeToken<Map<String, Field>>(){}.getType());

        fieldMapClone.get("password").setShow(false);

        return fieldMapClone;
    }
}
