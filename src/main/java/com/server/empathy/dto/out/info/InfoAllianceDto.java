package com.server.empathy.dto.out.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoAllianceDto {
    private String name;
    private String kind;
    private String locatiionStr;
    private String imageURL;
    private String targetId;
}
