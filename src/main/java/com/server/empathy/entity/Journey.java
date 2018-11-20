package com.server.empathy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "journey_tbl" , indexes = {@Index(name = "loc_code_index" , columnList = "location_code" , unique = false)})
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long jId;
    @NotNull // NotNull 작업 처리해야한다.
    private String title;
    private String contents;
    private String imageUrl;
    // journey는 mapX mapY 가 필요가 없음
//    private String mapX;
//    private String mapY;
    private String location;
    // code를 foreign key 로 줘서 검색을 빠르게 한다.
    @Column(name = "location_code")
    private int locationCode;

    @CreationTimestamp
    private Timestamp creationDate;
    // creationDate -> yyyy.MM.dd 형식 -> stampUtil
    @ManyToOne
    @JoinColumn(name = "owner_id") // foreign key check
    private User owner;

}
