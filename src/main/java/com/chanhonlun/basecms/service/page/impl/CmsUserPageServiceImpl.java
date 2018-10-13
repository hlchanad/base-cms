package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.FieldType;
import com.chanhonlun.basecms.form.CmsUserForm;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.pojo.CmsUser;
import com.chanhonlun.basecms.pojo.CmsUserRole;
import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.CmsUserRepository;
import com.chanhonlun.basecms.repository.RoleRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.response.vo.FieldOption;
import com.chanhonlun.basecms.response.vo.row.CmsUserRowVO;
import com.chanhonlun.basecms.service.data.CmsUserRoleService;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.CmsUserDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.CmsUserPageService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CmsUserPageServiceImpl extends BasePageServiceImpl implements CmsUserPageService {

    @Autowired
    private CmsUserDataTableServiceImpl cmsUserDataTablesService;

    @Autowired
    private CmsUserRepository cmsUserRepository;

    @Autowired
    private CmsUserRoleService cmsUserRoleService;

    @Autowired
    private RoleRepository roleRepository;

    private Map<String, Field> fieldMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        ReflectionUtil.getClassFields(CmsUser.class)
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .map(property -> new ImmutablePair<>(property.getName(), ReflectionUtil.getFieldFromProperty(property)))
                .forEach(pair -> fieldMap.put(pair.getKey(), pair.getValue()));

        fieldMap.put("userRoles", Field.builder()
                .id("userRoles")
                .title("User Role")
                .type(FieldType.MULTI_SELECT)
                .hintDetail("Select at least one role")
                .build());

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
    public Map<String, Field> getFieldMapForCreate() {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(this.fieldMap);

        List<Role> roles = roleRepository.findByIsDeleteFalse();

        List<FieldOption> fieldOptions = roles.stream()
                .map(role -> FieldOption.builder()
                        .id("roleId-" + role.getId())
                        .title(role.getTitle())
                        .value(role.getId().toString())
                        .build())
                .collect(Collectors.toList());

        fieldMapClone.get("userRoles").setOptions(fieldOptions);

        return fieldMapClone;
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
    public FormError ifCreateError(CmsUserForm form) {

        CmsUser cmsUser = cmsUserRepository.findByUsernameAndIsDeleteFalse(form.getUsername());

        if (cmsUser != null) {
            return new FormError("Duplicated username, please change your username");
        }

        return null;
    }

    @Override
    public CmsUser create(CmsUserForm form) {

        CmsUser cmsUser = new CmsUser();
        cmsUser.setUsername(form.getUsername());
        cmsUser.setPassword(form.getPassword());
        cmsUser.setEmail(form.getEmail());
        cmsUser = create(cmsUser);

        for (Long roleId : form.getUserRoles()) {
            CmsUserRole  cmsUserRole = new CmsUserRole();
            cmsUserRole.setCmsUserId(cmsUser.getId());
            cmsUserRole.setRoleId(roleId);
            cmsUserRole = cmsUserRoleService.create(cmsUserRole);
        }

        return cmsUser;
    }

    @Override
    public Map<String, Field> getFieldMapForEdit(CmsUser cmsUser) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(fieldMap);

        fieldMapClone.get("username").setDisabled(true);
        fieldMapClone.get("password").setShow(false);

        return fieldMapClone;
    }

    @Override
    public Map<String, Field> getFieldMapForDetail(CmsUser cmsUser) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(fieldMap);

        fieldMapClone.get("password").setShow(false);

        return fieldMapClone;
    }
}
