package com.server.empathy.dto.in.journey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatchJourneyInfoDto {
    private Long journeyId;
    private String title;
    private String contents;

}
