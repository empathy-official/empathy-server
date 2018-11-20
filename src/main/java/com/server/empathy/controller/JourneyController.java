package com.server.empathy.controller;

import com.server.empathy.dto.in.journey.CreateJourneyDto;
import com.server.empathy.dto.in.journey.PatchJourneyInfoDto;
import com.server.empathy.dto.out.journey.GetJourneyDto;
import com.server.empathy.dto.out.journey.GetJourneySimpleDto;
import com.server.empathy.entity.Location;
import com.server.empathy.entity.User;
import com.server.empathy.exception.BaseException;
import com.server.empathy.service.journey.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/journey")
public class JourneyController {

    @Autowired
    JourneyService journeyService;

    @GetMapping("/{targetId}")
    public GetJourneyDto getJourney(
            @PathVariable(name = "targetId") String targetId
    ){
        Long journeyId = Long.parseLong(targetId);
        return journeyService.getJourney(journeyId);
    }

    @GetMapping("/location/{locationEnum}")
    public List<GetJourneySimpleDto> getJourneyByLocation(
            @PathVariable(name = "locationEnum") String locationEnumStr
    ){
        int locationEnumInt = Location.valueOf(locationEnumStr).getCode();
        // service ( locaenum ) pageable? > ok 5개씩?
        return journeyService.getJourneyByLocation(locationEnumInt , 0 , 5);
    }

    @PostMapping("/")
    public void createJourney(
            @RequestPart(value = "ownerId") String ownerId ,
            @RequestPart(value = "title") String title,
            @RequestPart(value = "contents") String contents,
            @RequestPart(value = "location") String location,
            @RequestPart(value = "locationEnum") String locationEnum ,
            // 참고로 ModelAttribute는 쿼리 방식이다.
            @RequestParam(value = "file") MultipartFile file
            ){

        Location locEnum;
        try {
            locEnum = Location.valueOf(locationEnum);
        }catch (Exception e){
            throw new BaseException("Enum값을 확인해주세요");
        }
        Long targetId = Long.parseLong(ownerId);

        // file 이 제대로 들어오기는 함
        // s3 logic 통해서 imageurl 만들어야 함



        CreateJourneyDto dto = CreateJourneyDto.builder()
                .contents(contents)
                .location(location)
                .locationEnum(locEnum)
                .ownerId(targetId)
                .title(title)
                .file(file)
//                .imageUrl("hello world")
                .build();

        journeyService.createJourney(dto);
    }

    @PatchMapping("/")
    public void patchJourney(
            @RequestPart(value = "targetId") String targetId,
            @RequestPart(value = "title" , required = false) String title,
            @RequestPart(value = "contents" , required = false) String contents,
            @RequestPart(value = "image" , required = false) MultipartFile image
    ){
        Long journeyId = Long.parseLong(targetId);
        // info 변경
        if(title != null || contents != null){
            PatchJourneyInfoDto dto = PatchJourneyInfoDto.builder()
                    .journeyId(journeyId)
                    .title(title)
                    .contents(contents)
                    .build();
            journeyService.updateJourneyInfo(dto);
        }
        // image 변경
        if(image != null) journeyService.updateJourneyImage(journeyId , image);
    }

    @DeleteMapping("/{targetId}")
    public void deleteJourney(
            @PathVariable(name = "targetId") String targetId
    ){
        Long journeyId = Long.parseLong(targetId);
        journeyService.deleteJourney(journeyId);
    }

}

// request part는 file과 함께 들어온다.
// require 의 defuault 는 true 이다.
