package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.annotation.IgnoreAutoReflection;
import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.constant.MyConstants;
import com.chanhonlun.basecms.constant.Status;
import com.chanhonlun.basecms.form.BaseForm;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.form.PostForm;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.pojo.PostDetail;
import com.chanhonlun.basecms.repository.BaseDetailRepository;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.PostDetailRepository;
import com.chanhonlun.basecms.repository.PostRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.Field;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.page.DefaultCreatePageConfig;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.PostDataTableServiceImpl;
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
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class PostServiceImpl extends BaseServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostDetailRepository postDetailRepository;

    @Autowired
    private PostDataTableServiceImpl postDataTableService;

    // field name -> field
    private Map<String, Field> fieldMap = new LinkedHashMap<>();

    // field name -> (language -> field)
    private Map<String, Map<Language, Field>> fieldDetailMap = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        ReflectionUtil.getClassFields(Post.class)
                .stream()
                .filter(property -> property.getAnnotation(IgnoreAutoReflection.class) == null)
                .map(property -> new ImmutablePair<>(property.getName(), ReflectionUtil.getFieldFromProperty(property)))
                .forEach(pair -> fieldMap.put(pair.getKey(), pair.getValue()));

        ReflectionUtil.getClassFields(PostDetail.class)
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

    }

    @Override
    public BaseRepository<Post, Long> getRepository() {
        return postRepository;
    }

    @Override
    public BaseDetailRepository<PostDetail, Long, Long> getDetailRepository() {
        return postDetailRepository;
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
    public Map<String, Field> getFieldMap() {
        return fieldMap;
    }

    @Override
    public Map<String, Map<Language, Field>> getFieldDetailMap() {
        return fieldDetailMap;
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
    public void updateFieldMapValues(Map<String, Field> fieldMap, PostForm form) {
        fieldMap.get("publishDate").setValue(form.getPublishDate().toString());
    }

    @Override
    public void updateFieldDetailMapValues(Map<String, Map<Language, Field>> fieldDetailMap, PostForm form) {
        fieldDetailMap.get("title").get(Language.EN).setValue(form.getDetailEn().getTitle());
        fieldDetailMap.get("brief").get(Language.EN).setValue(form.getDetailEn().getBrief());
        fieldDetailMap.get("content").get(Language.EN).setValue(form.getDetailEn().getContent());

        fieldDetailMap.get("title").get(Language.ZH_HK).setValue(form.getDetailZhHk().getTitle());
        fieldDetailMap.get("brief").get(Language.ZH_HK).setValue(form.getDetailZhHk().getBrief());
        fieldDetailMap.get("content").get(Language.ZH_HK).setValue(form.getDetailZhHk().getContent());
    }

    @Override
    public FormError ifError(PostForm form) {

        if (form.getDetailEn().getTitle().equals("error")) {
            return new FormError("\"error\" is not allowed !");
        }

        return null;
    }

}
