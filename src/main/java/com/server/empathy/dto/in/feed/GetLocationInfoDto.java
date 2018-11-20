package com.server.empathy.dto.in.feed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetLocationInfoDto {
    private String mapX;
    private String mapY;
    private String rangeNum;
}
