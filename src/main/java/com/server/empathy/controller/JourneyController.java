package com.server.empathy.controller;

import com.server.empathy.dto.in.journey.CreateJourneyDto;
import com.server.empathy.entity.Location;
import com.server.empathy.entity.User;
import com.server.empathy.service.journey.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

    @RestController
    @RequestMapping("/journey")
    public class JourneyController {
        @Autowired
    JourneyService journeyService;

    @PostMapping("/")
    public void createJourney(
            @RequestPart("ownerId") String ownerId ,
            @RequestPart("title") String title,
            @RequestPart("contents") String contents,
            @RequestPart("location") String location ,
            // 참고로 ModelAttribute는 쿼리 방식이다.
            @RequestParam("file") MultipartFile file
            ){

        Location loc = Location.valueOf(location);
        if(loc == null) System.out.println("e check");
        else System.out.println("e good");

        Long targetId = Long.parseLong(ownerId);

        // file 이 제대로 들어오기는 함
        // s3 logic 통해서 imageurl 만들어야 함

        CreateJourneyDto dto = CreateJourneyDto.builder()
                .contents(contents)
                .location(loc)
                .ownerId(targetId)
                .title(title)
                .imageUrl("hello world")
                .build();


    }
}
