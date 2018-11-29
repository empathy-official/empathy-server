package com.server.empathy.dto.out.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoAllianceDetailDto {
    private String imageURL;
    private String title;
    private String overview;
    private String duration;
    private String playTime;
    private String priceInfo;
    private String locationStr;
    private String mapx;
    private String mapy;
}
