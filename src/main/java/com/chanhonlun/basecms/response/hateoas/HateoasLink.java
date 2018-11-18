package com.chanhonlun.basecms.response.hateoas;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HateoasLink {

    private String rel;

    private String href;

    public HateoasLink(String rel, String href) {
        this.rel = rel;
        this.href = href;
    }
}
