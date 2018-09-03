package com.chanhonlun.basecms.service.impl;

import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.model.*;
import com.chanhonlun.basecms.pojo.CmsMenu;
import com.chanhonlun.basecms.repository.CmsMenuRepository;
import com.chanhonlun.basecms.req.datatables.CmsMenuListDataTablesInput;
import com.chanhonlun.basecms.service.CmsMenuService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.vo.CmsMenuTableVO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CmsMenuServiceImpl extends BaseServiceImpl implements CmsMenuService {

    @Autowired
    private CmsMenuDataTablesService cmsMenuDataTablesService;

    @Autowired
    private BreadcrumbUtil breadcrumbUtil;

    @Autowired
    private CmsMenuRepository cmsMenuRepository;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public DataTablesOutput<CmsMenuTableVO> cmsMenuDataTablesAPI(CmsMenuListDataTablesInput input) {
        return cmsMenuDataTablesService.getDataTablesData(input);
    }

    @Override
    public BaseListConfig getListConfig() {
        return DefaultListConfig.builder()
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs(Arrays.asList(
                        Breadcrumb.builder()
                                .title("CMS Menu")
                                .url("/cms-menu")
                                .build()
                )))
                .datatable(cmsMenuDataTablesService.getDataTablesConfig(new HashMap<>()))
                .menu(getMenusConfig())
                .build();
    }

    @Override
    public CmsMenu findByIdAndIsDeleteFalse(Long id) {
        return cmsMenuRepository.findByIdAndIsDeleteFalse(id);
    }

    @Override
    public CmsMenu softDelete(CmsMenu cmsMenu) {
        cmsMenu.setUpdatedAt(new Date());
        cmsMenu.setUpdatedBy(MyConstants.USER_SYSTEM);
        cmsMenu.setIsDelete(true);
        return cmsMenuRepository.save(cmsMenu);
    }

    @Override
    public List<MenuItem> getMenusConfig() {

        List<MenuItem> menuItems = cmsMenuRepository.findByParentIdNullAndIsDeleteFalse(new Sort(Sort.Direction.ASC, "sequence"))
                .stream()
                .map(cmsMenu -> new MenuItem(cmsMenu, contextPath))
                .collect(Collectors.toList());

        cmsMenuRepository.findByParentIdNotNullAndIsDeleteFalse(new Sort(Sort.Direction.ASC, "parentId", "sequence"))
                .forEach(cmsMenu -> {
                    Optional<MenuItem> search = menuItems.stream().filter(menuItem -> menuItem.getId().equals(cmsMenu.getParentId())).findAny();

                    if (!search.isPresent()) return ;

                    search.get().getChildren().add(new MenuItem(cmsMenu, contextPath));
                });

        return menuItems;
    }

}
