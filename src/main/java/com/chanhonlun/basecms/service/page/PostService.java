package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.form.PostForm;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.pojo.PostDetail;
import com.chanhonlun.basecms.response.page.BaseCreatePageConfig;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCreatePageWithPojoDetail;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDetailPageWithPojoDetail;

public interface PostService extends
        BaseService,
        DefaultServiceHasDataTable<Post, Long, PostRowVO>,
        DefaultServiceHasCRUD<Post, Long>,
        DefaultServiceHasCreatePageWithPojoDetail,
        DefaultServiceHasDetailPageWithPojoDetail<Post, Long, PostDetail, Long> {

    Post create(PostForm form);

}
