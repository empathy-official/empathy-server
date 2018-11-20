package com.server.empathy.dto.out.journey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetJourneyDto {
    private Long journeyId;
    private String imageUrl;
    private String ownerProfileUrl;
    private String creationTime;
    private String title;
    private String contents;
    private String location;
}
