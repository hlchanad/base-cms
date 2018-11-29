package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.pojo.Image;
import com.chanhonlun.basecms.request.ImageCreateRequest;
import com.chanhonlun.basecms.request.Paging;
import com.chanhonlun.basecms.response.hateoas.ImagesHateoasVO;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;

import java.io.InputStream;

public interface ImageService extends DefaultServiceHasCRUD<Image, Long> {

    Image create(ImageCreateRequest request);

    ImagesHateoasVO listWithHateoas(Paging paging, String section);

    InputStream getInputStream(Image image);

    Image findByFileNameAndIsDeletedFalse(String fileName);
}
