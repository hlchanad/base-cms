package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.form.RoleForm;
import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.RoleRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.vo.row.RoleRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.RoleDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.RoleService;
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
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleDataTableServiceImpl roleDataTableService;

    // field name -> field
    private Map<String, Field> fieldMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        ReflectionUtil.getClassFields(Role.class)
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .map(property -> new ImmutablePair<>(property.getName(), ReflectionUtil.getFieldFromProperty(property)))
                .forEach(pair -> fieldMap.put(pair.getKey(), pair.getValue()));
    }

    @Override
    public BaseRepository<Role, Long> getRepository() {
        return roleRepository;
    }

    @Override
    public BaseDataTableService<Role, Long, RoleRowVO, BaseDataTableInput, BaseDataTableConfig> getDataTablesService() {
        return roleDataTableService;
    }

    @Override
    public SidebarMenuUtil getSidebarMenuUtil() {
        return sidebarMenuUtil;
    }

    @Override
    public BreadcrumbUtil getBreadcrumbUtil() {
        return breadcrumbUtil;
    }

    @Override
    public Map<String, Field> getFieldMap() {
        return fieldMap;
    }

    @Override
    public void updateFieldMapValues(Map<String, Field> fieldMap, RoleForm form) {
        fieldMap.get("code").setValue(form.getCode());
        fieldMap.get("title").setValue(form.getTitle());
        fieldMap.get("description").setValue(form.getDescription());
    }

    @Override
    public FormError ifCreateError(RoleForm form) {

        Role role = roleRepository.findByCodeAndIsDeleteFalse(form.getCode());

        if (role != null) {
            return new FormError("Code \"" + form.getCode() + "\" has been used.");
        }

        return null;
    }

    @Override
    public Role create(RoleForm form) {

        Role role = new Role();
        role.setCode(form.getCode());
        role.setTitle(form.getTitle());
        role.setDescription(form.getDescription());
        role = create(role);

        return role;
    }

    @Override
    public Map<String, Field> getFieldMapForEdit() {

        Gson gson = new Gson();

        Map<String, Field> fieldMapClone = gson.fromJson(gson.toJson(fieldMap),
                new TypeToken<Map<String, Field>>() {}.getType());

        fieldMapClone.get("code").setDisabled(true);
        fieldMapClone.get("title").setDisabled(true);

        return fieldMapClone;
    }

    @Override
    public FormError ifEditError(Role pojo, RoleForm form) {
        return null;
    }

    @Override
    public Role edit(Role role, RoleForm form) {

        role.setDescription(form.getDescription());
        role = update(role);

        return role;
    }
}
