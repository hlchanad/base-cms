package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.constant.FieldType;
import com.chanhonlun.basecms.constant.Status;
import com.chanhonlun.basecms.form.CmsMenuForm;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.pojo.CmsMenuRole;
import com.chanhonlun.basecms.pojo.Role;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.CmsMenuRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.response.vo.FieldOption;
import com.chanhonlun.basecms.response.vo.row.CmsMenuRowVO;
import com.chanhonlun.basecms.service.data.CmsMenuRoleService;
import com.chanhonlun.basecms.service.data.RoleService;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.CmsMenuDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.CmsMenuPageService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ListUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CmsMenuPageServiceImpl extends BasePageServiceImpl implements CmsMenuPageService {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private CmsMenuDataTableServiceImpl cmsMenuDataTablesService;

    @Autowired
    private CmsMenuRepository cmsMenuRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CmsMenuRoleService cmsMenuRoleService;

    private Map<String, Field> fieldMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        this.fieldMap = ReflectionUtil.getFieldMap(CmsMenu.class);

        fieldMap.get("url").setRequired(false);

        fieldMap.get("parentId").setTitle("Parent");
        fieldMap.get("parentId").setRequired(false);
        fieldMap.get("parentId").setType(FieldType.DROPDOWN);

        fieldMap.put("roles", Field.builder()
                .id("roles")
                .title("Roles")
                .type(FieldType.MULTI_SELECT)
                .hintDetail("Select at least one role")
                .build());
    }

    @Override
    public String getPageTitle() {
        return "CMS Menu";
    }

    @Override
    public BaseRepository<CmsMenu, Long> getRepository() {
        return cmsMenuRepository;
    }

    @Override
    public BaseDataTableService<CmsMenu, Long, CmsMenuRowVO, BaseDataTableInput, BaseDataTableConfig> getDataTablesService() {
        return cmsMenuDataTablesService;
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
    public Map<String, Field> getFieldMapForCreate() {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(fieldMap);

        fieldMapClone.get("parentId").setOptions(getParentIdFieldOptions(null));

        fieldMapClone.get("roles").setOptions(getRolesFieldOptions());

        return fieldMapClone;
    }

    @Override
    public Map<String, Field> getFieldMapForEdit(CmsMenu cmsMenu) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(fieldMap);

        fieldMapClone.get("parentId").setOptions(getParentIdFieldOptions(cmsMenu.getId()));

        fieldMapClone.get("roles").setOptions(getRolesFieldOptions());
        fieldMapClone.get("roles").setMultiValues(getSelectedRolesValue(cmsMenu.getId()));

        return fieldMapClone;
    }

    @Override
    public Map<String, Field> getFieldMapForDetail(CmsMenu cmsMenu) {

        Map<String, Field> fieldMapClone = ReflectionUtil.cloneFieldMap(fieldMap);

        String roles = cmsMenuRoleService.findByCmsMenuIdAndIsDeletedFalse(cmsMenu.getId())
                .stream()
                .map(CmsMenuRole::getRoleId)
                .map(roleService::findByIdAndIsDeletedFalse)
                .map(Role::getTitle)
                .collect(Collectors.joining(", "));


        fieldMapClone.get("roles").setValue(roles);

        return fieldMapClone;
    }

    @Override
    public void updateFieldMapValues(Map<String, Field> fieldMap, CmsMenuForm form) {
        fieldMap.get("parentId").setValue(form.getParentId().toString());
        fieldMap.get("title").setValue(form.getTitle());
        fieldMap.get("url").setValue(form.getUrl());
        fieldMap.get("icon").setValue(form.getIcon());
        fieldMap.get("sequence").setValue(form.getSequence().toString());
        fieldMap.get("roles").setMultiValues(form.getRoles().stream().map(Object::toString).collect(Collectors.toList()));
    }

    @Override
    public CmsMenu edit(CmsMenu cmsMenu, CmsMenuForm form) {
        cmsMenu.setParentId(form.getParentId());
        cmsMenu.setTitle(form.getTitle());
        cmsMenu.setUrl(form.getUrl());
        cmsMenu.setIcon(form.getIcon());
        cmsMenu.setSequence(form.getSequence());
        cmsMenu = update(cmsMenu);


        List<CmsMenuRole> cmsMenuRoles = cmsMenuRoleService.findByCmsMenuIdAndIsDeletedFalse(cmsMenu.getId());
        List<Long> existingRoleIds = cmsMenuRoles.stream().map(CmsMenuRole::getRoleId).collect(Collectors.toList());

        List<Long> intersection = ListUtil.getIntersection(Long.class, existingRoleIds, form.getRoles());

        List<Long> newRoleIds = new ArrayList<>(form.getRoles());
        newRoleIds.removeAll(intersection);

        List<Long> toBeDeletedRoleIds = new ArrayList<>(existingRoleIds);
        toBeDeletedRoleIds.removeAll(intersection);

        for (Long roleId : newRoleIds) {
            CmsMenuRole cmsMenuRole = new CmsMenuRole();
            cmsMenuRole.setCmsMenuId(cmsMenu.getId());
            cmsMenuRole.setRoleId(roleId);
            cmsMenuRole = cmsMenuRoleService.create(cmsMenuRole);
        }

        for (Long roleId : toBeDeletedRoleIds) {
            CmsMenuRole cmsMenuRole = cmsMenuRoles
                    .stream()
                    .filter(cmsMenuRole1 -> cmsMenuRole1.getRoleId().equals(roleId))
                    .findAny()
                    .orElse(null);
            cmsMenuRoleService.softDelete(cmsMenuRole);
        }

        return cmsMenu;
    }

    @Override
    public CmsMenu create(CmsMenuForm form) {
        CmsMenu cmsMenu = new CmsMenu();
        cmsMenu.setParentId(form.getParentId());
        cmsMenu.setTitle(form.getTitle());
        cmsMenu.setUrl(form.getUrl());
        cmsMenu.setIcon(form.getIcon());
        cmsMenu.setSequence(form.getSequence());
        cmsMenu.setStatus(Status.NORMAL);
        cmsMenu = create(cmsMenu);

        for (Long roleId : form.getRoles()) {
            CmsMenuRole cmsMenuRole = new CmsMenuRole();
            cmsMenuRole.setCmsMenuId(cmsMenu.getId());
            cmsMenuRole.setRoleId(roleId);
            cmsMenuRole.setStatus(Status.NORMAL);
            cmsMenuRole = cmsMenuRoleService.create(cmsMenuRole);
        }

        return cmsMenu;
    }

    @Override
    public List<CmsMenu> findByIsDeletedFalse() {
        return cmsMenuRepository.findByIsDeletedFalse(new Sort(Sort.Direction.ASC, "sequence"));
    }

    @Override
    public void refreshSidebarMenu() {
        sidebarMenuUtil.updateMenu();
    }

    @Override
    public List<Role> findRoleByCmsMenuIdAndIsDeletedFalse(Long cmsMenuId) {
        return cmsMenuRoleService.findByCmsMenuIdAndIsDeletedFalse(cmsMenuId)
                .stream()
                .map(CmsMenuRole::getRoleId)
                .map(roleService::findByIdAndIsDeletedFalse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> getAllowedRoleIds(CmsMenu cmsMenu) {
        return Optional.ofNullable(cmsMenu.getRoles())
                .orElse(findRoleByCmsMenuIdAndIsDeletedFalse(cmsMenu.getId()))
                .stream()
                .map(Role::getId)
                .collect(Collectors.toList());
    }

    private List<FieldOption> getParentIdFieldOptions(Long id) {

        List<CmsMenu> cmsMenus = id == null
                ? findByIsDeletedFalse()
                : cmsMenuRepository.findByIdNotAndIsDeletedFalse(id);

        List<FieldOption> fieldOptions = new ArrayList<>();

        fieldOptions.add(FieldOption.builder()
                .id("parentId-null")
                .title("Root")
                .value("")
                .build());

        fieldOptions.addAll(cmsMenus.stream()
                .map(cmsMenu -> FieldOption.builder()
                        .id("parentId-" + cmsMenu.getId())
                        .title(cmsMenu.getTitle())
                        .value(cmsMenu.getId().toString())
                        .build())
                .collect(Collectors.toList()));

        return fieldOptions;
    }

    private List<FieldOption> getRolesFieldOptions() {
        return roleService.findBySelectableTrueAndIsDeletedFalse()
                .stream()
                .map(role -> FieldOption.builder()
                        .id("roleId-" + role.getId())
                        .title(role.getTitle())
                        .value(role.getId().toString())
                        .build())
                .collect(Collectors.toList());
    }

    private List<String> getSelectedRolesValue(Long id) {
        List<CmsMenuRole> cmsMenuRoles = id == null
                ? Collections.emptyList()
                : cmsMenuRoleService.findByCmsMenuIdAndIsDeletedFalse(id);

        return cmsMenuRoles.stream()
                .map(CmsMenuRole::getRoleId)
                .map(Object::toString)
                .collect(Collectors.toList());

    }
}
