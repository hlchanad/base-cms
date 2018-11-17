package com.chanhonlun.basecms.service;

import com.chanhonlun.basecms.pojo.Image;
import com.chanhonlun.basecms.request.ImageCreateRequest;
import com.chanhonlun.basecms.service.trait.DefaultServiceHasCRUD;

public interface ImageService extends DefaultServiceHasCRUD<Image, Long> {

    Image create(ImageCreateRequest request);
}
