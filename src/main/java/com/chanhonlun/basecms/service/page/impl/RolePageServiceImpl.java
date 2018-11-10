package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.constant.FieldType;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.form.RoleForm;
import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.pojo.RoleRoute;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.RoleRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.response.vo.FieldOption;
import com.chanhonlun.basecms.response.vo.row.RoleRowVO;
import com.chanhonlun.basecms.service.data.RoleRouteService;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.RoleDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.RolePageService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ListUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RolePageServiceImpl extends BasePageServiceImpl implements RolePageService {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleRouteService roleRouteService;

    @Autowired
    private RoleDataTableServiceImpl roleDataTableService;

    // field name -> field
    private Map<String, Field> fieldMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        this.fieldMap = ReflectionUtil.getFieldMap(Role.class);

        fieldMap.put("roleRoutes", Field.builder()
                .id("roleRoutes")
                .title("Allowed Routes")
                .type(FieldType.MULTI_SELECT)
                .build());
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
        fieldMap.get("roleRoutes").setMultiValues(form.getRoleRoutes());
    }

    @Override
    public Map<String, Field> getFieldMapForCreate() {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(this.fieldMap);

        fieldMapClone.get("roleRoutes").setOptions(getRouteOptions());

        return fieldMapClone;
    }

    @Override
    public FormError ifCreateError(RoleForm form) {

        Role role = roleRepository.findByCodeAndIsDeletedFalse(form.getCode());

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
        role.setSelectable(true);
        role = create(role);

        roleRouteService.batchCreate(role.getId(), form.getRoleRoutes());

        return role;
    }

    @Override
    public Map<String, Field> getFieldMapForEdit(Role role) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(fieldMap);

        fieldMapClone.get("code").setDisabled(true);
        fieldMapClone.get("title").setDisabled(true);

        fieldMapClone.get("roleRoutes").setOptions(getRouteOptions());
        fieldMapClone.get("roleRoutes").setMultiValues(getSelectedRolesValue(role.getId()));

        return fieldMapClone;
    }

    @Override
    public Role edit(Role role, RoleForm form) {

        role.setDescription(form.getDescription());
        role = update(role);


        List<RoleRoute> roleRoutes = roleRouteService.findByRoleIdAndIsDeletedFalse(role.getId());
        List<String> existingRoutes = roleRoutes.stream().map(RoleRoute::getUrl).collect(Collectors.toList());

        List<String> intersection = ListUtil.getIntersection(String.class, existingRoutes, form.getRoleRoutes());

        List<String> newRoutes = new ArrayList<>(form.getRoleRoutes());
        newRoutes.removeAll(intersection);

        List<String> toBeDeletedRoutes = new ArrayList<>(existingRoutes);
        toBeDeletedRoutes.removeAll(intersection);

        roleRouteService.batchCreate(role.getId(), newRoutes);

        for (String route : toBeDeletedRoutes) {
            RoleRoute roleRoute = roleRoutes
                    .stream()
                    .filter(roleRoute1 -> roleRoute1.getUrl().equals(route))
                    .findAny()
                    .orElse(null);
            roleRouteService.softDelete(roleRoute);
        }

        return role;
    }

    @Override
    public Map<String, Field> getFieldMapForDetail(Role role) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(this.fieldMap);

        fieldMapClone.get("roleRoutes").setValue(getSelectedRolesValue(role.getId()).stream().collect(Collectors.joining(", ")));

        return fieldMapClone;
    }

    private List<FieldOption> getRouteOptions() {
        return ListUtil.mergeLists(
                String.class,
                Collections.singletonList("/"),
                ReflectionUtil.getControllerMappings(requestMappingHandlerMapping)
        )
                .stream()
                .map(mapping -> FieldOption.builder()
                        .id("roleRoutes-" + mapping)
                        .title(mapping)
                        .value(mapping)
                        .build())
                .collect(Collectors.toList());
    }

    private List<String> getSelectedRolesValue(Long id) {
        List<RoleRoute> roleRoutes = id == null
                ? Collections.emptyList()
                : roleRouteService.findByRoleIdAndIsDeletedFalse(id);

        logger.info("roleRoutes: {}", roleRoutes);

        return roleRoutes.stream()
                .map(RoleRoute::getUrl)
                .collect(Collectors.toList());
    }
}
