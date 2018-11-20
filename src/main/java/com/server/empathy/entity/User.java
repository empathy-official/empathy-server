package com.server.empathy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_tbl")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column(length = 50)
    private String name;
    @Column(length = 10)
    private String loginApi;
    private String profileUrl;
    // 일대다 피드 연결
    // [Journey list]
    @OneToMany(mappedBy = "owner" ,cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
    private List<Journey> journeyList;
    @CreationTimestamp
    private Timestamp creationDate;

}
