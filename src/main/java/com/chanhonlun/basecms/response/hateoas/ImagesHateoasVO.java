package com.chanhonlun.basecms.response.hateoas;

import com.chanhonlun.basecms.response.vo.ImageVO;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Data
public class ImagesHateoasVO {

    private Long total;

    private List<ImageVO> images;

    private Map<String, HateoasLink> links;

    public ImagesHateoasVO(List<ImageVO> imageVOs, Map<String, HateoasLink> links, long totalElements) {
        this.total = totalElements;
        this.images = imageVOs;
        this.links = links;
    }
}
