package com.server.empathy.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "filter_tbl")
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long filterId;
    @Column(length = 50)
    private String type;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String standard;
    @Column(length = 50)
    private String gravity;
    private Boolean alingLeft;
    private String imageURL;

}
