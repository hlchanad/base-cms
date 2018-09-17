package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.constant.Status;
import com.chanhonlun.basecms.form.PostForm;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.pojo.PostDetail;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.PostDetailRepository;
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
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class PostServiceImpl extends BaseServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostDetailRepository postDetailRepository;

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

        return BaseCreatePageConfig.builder()
                .pageTitle("Post")
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .menu(sidebarMenuUtil.getSidebarMenuList())
                .fields(new ArrayList<>(fieldMap.values()))
                .detailFields(ReflectionUtil.getDetailFields(fieldDetailMap))
                .build();
    }

    @Override
    public Post create(PostForm form) {

        Post post = new Post();
        post.setPublishDate(form.getPublishDate());
        post.setIsDelete(false);
        post.setStatus(Status.NORMAL);
        post.setCreatedAt(new Date());
        post.setCreatedBy(MyConstants.USER_SYSTEM);
        post.setUpdatedAt(new Date());
        post.setUpdatedBy(MyConstants.USER_SYSTEM);
        post = postRepository.save(post);

        PostDetail postDetailEn = new PostDetail();
        postDetailEn.setRefId(post.getId());
        postDetailEn.setLang(Language.EN);
        postDetailEn.setTitle(form.getDetailEn().getTitle());
        postDetailEn.setBrief(form.getDetailEn().getBrief());
        postDetailEn.setContent(form.getDetailEn().getContent());
        postDetailRepository.save(postDetailEn);

        PostDetail postDetailZhHk = new PostDetail();
        postDetailZhHk.setRefId(post.getId());
        postDetailZhHk.setLang(Language.ZH_HK);
        postDetailZhHk.setTitle(form.getDetailZhHk().getTitle());
        postDetailZhHk.setBrief(form.getDetailZhHk().getBrief());
        postDetailZhHk.setContent(form.getDetailZhHk().getContent());
        postDetailRepository.save(postDetailZhHk);

        return post;
    }

    @Override
    public BaseCreatePageConfig getDetailPageConfig(Post post) {

        PostDetail postDetailEn   = postDetailRepository.findByRefIdAndLang(post.getId(), Language.EN);
        PostDetail postDetailZhHk = postDetailRepository.findByRefIdAndLang(post.getId(), Language.ZH_HK);

        Gson gson = new Gson();

        Map<String, Field> fieldMap =
                gson.fromJson(gson.toJson(this.fieldMap), new TypeToken<Map<String, Field>>(){}.getType());

        Map<String, Map<Language, Field>> fieldDetailMap =
                gson.fromJson(gson.toJson(this.fieldDetailMap), new TypeToken<Map<String, Map<Language, Field>>>(){}.getType());

        fieldMap.get("publishDate").setValue(post.getPublishDate().toString());
        fieldDetailMap.get("title").get(Language.EN).setValue(postDetailEn.getTitle());
        fieldDetailMap.get("title").get(Language.ZH_HK).setValue(postDetailZhHk.getTitle());
        fieldDetailMap.get("brief").get(Language.EN).setValue(postDetailEn.getBrief());
        fieldDetailMap.get("brief").get(Language.ZH_HK).setValue(postDetailZhHk.getBrief());
        fieldDetailMap.get("content").get(Language.EN).setValue(postDetailEn.getContent().replaceAll("\\n", "<br/>"));
        fieldDetailMap.get("content").get(Language.ZH_HK).setValue(postDetailZhHk.getContent().replaceAll("\\n", "<br/>"));

        return BaseCreatePageConfig.builder()
                .pageTitle("Post")
                .breadcrumbs(breadcrumbUtil.getBreadcrumbs())
                .menu(sidebarMenuUtil.getSidebarMenuList())
                .fields(new ArrayList<>(fieldMap.values()))
                .detailFields(ReflectionUtil.getDetailFields(fieldDetailMap))
                .build();
    }

    @Override
    public BaseCreatePageConfig getDetailPageConfig(Long id) {
        return getDetailPageConfig(postRepository.findByIdAndIsDeleteFalse(id));
    }
}
