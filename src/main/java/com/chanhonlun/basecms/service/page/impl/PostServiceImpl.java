package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.pojo.PostDetail;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.PostRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.DetailField;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.PostDataTableService;
import com.chanhonlun.basecms.service.page.PostService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl extends BaseServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostDataTableService postDataTableService;

    // field name -> field
    private Map<String, Field> fieldMap = new LinkedHashMap<>();

    // field name -> (language -> field)
    private Map<String, Map<Language, Field>> fieldDetailMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        ReflectionUtil.getPojoFields(Post.class)
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .map(property -> new ImmutablePair<>(property.getName(), ReflectionUtil.getFieldFromProperty(property)))
                .forEach(pair -> fieldMap.put(pair.getKey(), pair.getValue()));

        ReflectionUtil.getPojoFields(PostDetail.class)
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .map(property -> {
                    Map<Language, Field> languageFieldMap = new LinkedHashMap<>();

                    for (Language language : Language.values()) {
                        languageFieldMap.put(language, ReflectionUtil.getFieldFromProperty(property, language));
                    }

                    return new ImmutablePair<>(property.getName(), languageFieldMap);
                })
                .forEach(pair -> fieldDetailMap.put(pair.getKey(), pair.getValue()));

        logger.info("fieldMap: {}", new Gson().toJson(fieldMap));
        logger.info("fieldDetailMap: {}", new Gson().toJson(fieldDetailMap));

    }

    @Override
    public BaseRepository<Post, Long> getRepository() {
        return postRepository;
    }

    @Override
    public BaseDataTableService<Post, Long, PostRowVO, BaseDataTableInput, BaseDataTableConfig> getDataTablesService() {
        return postDataTableService;
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

        List<DetailField> detailFields = new ArrayList<>();

        fieldDetailMap.values().stream()
                .flatMap(languageFieldMap -> languageFieldMap.entrySet().stream())
                .forEach(languageFieldEntry -> {

                    DetailField search = detailFields.stream()
                            .filter(detailField -> detailField.getLanguage().equals(languageFieldEntry.getKey()))
                            .findAny()
                            .orElse(null);

                    if (search == null) {
                        search = DetailField.builder()
                                .language(languageFieldEntry.getKey())
                                .fields(new ArrayList<>())
                                .build();
                        detailFields.add(search);
                    }

                    search.getFields().add(languageFieldEntry.getValue());
                });

        logger.info("detailFields: {}", new Gson().toJson(detailFields));

        return BaseCreatePageConfig.builder()
                .pageTitle("Post")
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .menu(sidebarMenuUtil.getSidebarMenuList())
                .fields(new ArrayList<>(fieldMap.values()))
                .detailFields(detailFields)
                .build();
    }
}
