package com.server.empathy.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "filter_list_tbl")
public class FilterList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long filterListId;
    @Column(length = 50)
    private String filterName;

}
