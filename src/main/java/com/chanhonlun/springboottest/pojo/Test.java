package com.chanhonlun.springboottest.pojo;

import javax.persistence.*;

@Entity
@Table(name = "TEST")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DEFAULT_ID_GENERATOR")
    @SequenceGenerator(name = "TEST_ID_GENERATOR", sequenceName = "TEST_ID_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
}
