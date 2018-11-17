package com.server.empathy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "journey_tbl")
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long jId;
    private String title;
    private String contetns;
    private String imageUrl;
    private String mapX;
    private String mapY;
    // location 을 enum 형식으로 하는것이 좋을 것 같음
    // zipcode 확인
    private String location;


    @CreationTimestamp
    private Timestamp creationDate;

    // creationDate -> yyyy.MM.dd 형식으로 뿌려줘야 한다.

}
