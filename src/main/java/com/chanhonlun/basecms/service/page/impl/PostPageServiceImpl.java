package com.chanhonlun.basecms.service.page.impl;

import com.chanhonlun.basecms.constant.FieldType;
import com.chanhonlun.basecms.constant.Language;
import com.chanhonlun.basecms.form.FormError;
import com.chanhonlun.basecms.form.PostForm;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.pojo.PostDetail;
import com.chanhonlun.basecms.repository.BaseDetailRepository;
import com.chanhonlun.basecms.repository.BaseRepository;
import com.chanhonlun.basecms.repository.PostDetailRepository;
import com.chanhonlun.basecms.repository.PostRepository;
import com.chanhonlun.basecms.request.datatable.BaseDataTableInput;
import com.chanhonlun.basecms.response.component.BaseDataTableConfig;
import com.chanhonlun.basecms.response.vo.Field;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.datatable.BaseDataTableService;
import com.chanhonlun.basecms.service.datatable.impl.PostDataTableServiceImpl;
import com.chanhonlun.basecms.service.page.PostPageService;
import com.chanhonlun.basecms.util.BreadcrumbUtil;
import com.chanhonlun.basecms.util.ReflectionUtil;
import com.chanhonlun.basecms.util.SidebarMenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class PostPageServiceImpl extends BasePageServiceImpl implements PostPageService {

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
        this.fieldMap = ReflectionUtil.getFieldMap(Post.class);
        this.fieldDetailMap = ReflectionUtil.getFieldDetailMap(PostDetail.class);

        fieldMap.get("thumbnail").setType(FieldType.IMAGE);

        fieldMap.get("publishDate").setHintDetail("Remember to set the date again before fixed");

        fieldDetailMap.get("content").get(Language.EN).setType(FieldType.LONG_TEXT);
        fieldDetailMap.get("content").get(Language.ZH_HK).setType(FieldType.LONG_TEXT);
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
    public FormError ifCreateError(PostForm form) {

        if (form.getDetailEn().getTitle().equals("error")) {
            return new FormError("\"error\" is not allowed !");
        }

        return null;
    }

    @Override
    public Post create(PostForm form) {

        Post post = new Post();
        post.setPublishDate(form.getPublishDate());
        post = create(post);

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
    public FormError ifEditError(Post post, PostForm form) {

        if (form.getDetailEn().getTitle().equals("error")) {
            return new FormError("\"error\" is not allowed for Title (En)");
        }

        return null;
    }

    @Override
    public Post edit(Post post, PostForm form) {

        post.setPublishDate(form.getPublishDate());
        post = update(post);

        PostDetail postDetailEn = getDetailRepository().findByRefIdAndLang(post.getId(), Language.EN);
        postDetailEn.setTitle(form.getDetailEn().getTitle());
        postDetailEn.setBrief(form.getDetailEn().getBrief());
        postDetailEn.setContent(form.getDetailEn().getContent());
        getDetailRepository().save(postDetailEn);

        PostDetail postDetailZhHk = getDetailRepository().findByRefIdAndLang(post.getId(), Language.ZH_HK);
        postDetailZhHk.setTitle(form.getDetailZhHk().getTitle());
        postDetailZhHk.setBrief(form.getDetailZhHk().getBrief());
        postDetailZhHk.setContent(form.getDetailZhHk().getContent());
        getDetailRepository().save(postDetailZhHk);

        return post;
    }

}
