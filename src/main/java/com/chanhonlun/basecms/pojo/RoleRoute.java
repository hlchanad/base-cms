package com.chanhonlun.basecms.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ROLE_ROUTE")
@SequenceGenerator(name = "SEQUENCE_GENERATOR", sequenceName = "ROLE_ROUTE_ID_SEQ", allocationSize = 1)
public class RoleRoute extends BasePojo<Long> {

    @Column(name = "ROLE_ID")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "METHOD")
    private HttpMethod method;

    @Column(name = "URL")
    private String url;

}
