package com.server.empathy.dto.out.journey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetJourneySimpleDto {
    private Long journeyId;
    private String imageUrl;
    private String ownerProfileUrl;
    private String ownerName;
//    private String title;

}
