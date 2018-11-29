package com.server.empathy.dto.out.journey;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMainJourneyInfo {
    private List<GetJourneySimpleDto> otherPeopleList;
    private String imageURL;
    private String isFirst;
    private String mainText;
    private String enumStr;
    private String weekday;
}
