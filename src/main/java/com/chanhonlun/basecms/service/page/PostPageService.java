package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.form.PostForm;
import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.pojo.PostDetail;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.trait.*;

public interface PostPageService extends
        BasePageService,
        DefaultServiceHasCRUD<Post, Long>,
        DefaultServiceHasDataTable<Post, Long, PostRowVO>,
        DefaultServiceHasCreatePageWithPojoDetail<Post, Long, PostForm>,
        DefaultServiceHasEditPageWithPojoDetail<Post, Long, PostDetail, Long, PostForm>,
        DefaultServiceHasDetailPageWithPojoDetail<Post, Long, PostDetail, Long> {

}
