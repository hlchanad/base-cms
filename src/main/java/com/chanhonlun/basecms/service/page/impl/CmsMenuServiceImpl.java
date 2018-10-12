package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.form.CmsMenuForm;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.CmsMenuRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.vo.row.CmsMenuRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.CmsMenuDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.CmsMenuService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CmsMenuServiceImpl extends BaseServiceImpl implements CmsMenuService {

    @Autowired
    private CmsMenuDataTableServiceImpl cmsMenuDataTablesService;

    @Autowired
    private CmsMenuRepository cmsMenuRepository;

    private Map<String, Field> fieldMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        ReflectionUtil.getClassFields(CmsMenu.class)
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .map(property -> new ImmutablePair<>(property.getName(), ReflectionUtil.getFieldFromProperty(property)))
                .forEach(pair -> fieldMap.put(pair.getKey(), pair.getValue()));

        fieldMap.get("parentId").setRequired(false);
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
    public void updateFieldMapValues(Map<String, Field> fieldMap, CmsMenuForm form) {
        fieldMap.get("parentId").setValue(form.getParentId().toString());
        fieldMap.get("title").setValue(form.getTitle());
        fieldMap.get("url").setValue(form.getUrl());
        fieldMap.get("icon").setValue(form.getIcon());
        fieldMap.get("sequence").setValue(form.getSequence().toString());
    }

    @Override
    public CmsMenu edit(CmsMenu cmsMenu, CmsMenuForm form) {
        cmsMenu.setParentId(form.getParentId());
        cmsMenu.setTitle(form.getTitle());
        cmsMenu.setUrl(form.getUrl());
        cmsMenu.setIcon(form.getIcon());
        cmsMenu.setSequence(form.getSequence());
        cmsMenu = update(cmsMenu);

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
        cmsMenu = create(cmsMenu);

        return cmsMenu;
    }

    @Override
    public List<CmsMenu> findByParentIdNullAndIsDeleteFalse() {
        return cmsMenuRepository.findByParentIdNullAndIsDeleteFalse(new Sort(Sort.Direction.ASC, "sequence"));
    }

    @Override
    public List<CmsMenu> findByParentIdAndIsDeleteFalse(Long parentId) {
        return cmsMenuRepository.findByParentIdAndIsDeleteFalse(parentId, new Sort(Sort.Direction.ASC, "sequence"));
    }

}
