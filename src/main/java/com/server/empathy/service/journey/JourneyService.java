package com.server.empathy.service.journey;

import com.server.empathy.dto.in.journey.CreateJourneyDto;
import com.server.empathy.dto.in.journey.PatchJourneyInfoDto;
import com.server.empathy.dto.out.journey.GetJourneyDto;
import com.server.empathy.dto.out.journey.GetJourneySimpleDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface JourneyService {
    GetJourneyDto getJourney(Long journeyId);
    List<GetJourneySimpleDto> getJourneyByLocation(int locationCode ,int page , int size);
    void createJourney(CreateJourneyDto dto);
    void updateJourneyInfo(PatchJourneyInfoDto dto);
    void updateJourneyImage(Long journeyId , MultipartFile file);
    void deleteJourney(Long journeyId);
}
