package com.server.empathy.dto.out.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTourAPIDetailDto {
    private String imageURL;
    private String title;
    private String overviewText;
    private String businessHours;
    private String dayOff;
    private String creditCard;
    private String withPet;
    private String locationStr;
    private String mapx;
    private String mapy;

}
