package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.pojo.Image;
import com.chanhonlun.basecms.request.ImageCreateRequest;
import com.chanhonlun.basecms.request.Paging;
import com.chanhonlun.basecms.response.hateoas.ImagesHateoasVO;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;
import org.springframework.data.domain.Page;

public interface ImageService extends DefaultServiceHasCRUD<Image, Long> {

    Image create(ImageCreateRequest request);

    Page<Image> list(Paging paging);

    ImagesHateoasVO listWithHateoas(Paging paging, String section);
}
