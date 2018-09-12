package com.chanhonlun.basecms.service.page;

import com.chanhonlun.basecms.pojo.Post;
import com.chanhonlun.basecms.response.vo.row.PostRowVO;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasDataTable;

public interface PostService extends
        BaseService,
        DefaultServiceHasDataTable<Post, Long, PostRowVO>,
        DefaultServiceHasCRUD<Post, Long> {
}
