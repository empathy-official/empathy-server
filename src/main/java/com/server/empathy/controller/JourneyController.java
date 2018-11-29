package com.server.empathy.controller;

import com.server.empathy.dto.in.journey.CreateJourneyDto;
import com.server.empathy.dto.in.journey.PatchJourneyInfoDto;
import com.server.empathy.dto.out.journey.GetJourneyDto;
import com.server.empathy.dto.out.journey.GetJourneySimpleDto;
import com.server.empathy.dto.out.journey.GetMainJourneyInfo;
import com.server.empathy.entity.Journey;
import com.server.empathy.entity.Location;
import com.server.empathy.entity.User;
import com.server.empathy.entity.WeekDaysKor;
import com.server.empathy.exception.BaseException;
import com.server.empathy.exception.NotFoundException;
import com.server.empathy.repository.JourneyRepository;
import com.server.empathy.repository.UserRepository;
import com.server.empathy.service.TimeStampUtil;
import com.server.empathy.service.journey.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/journey")
public class JourneyController {

    @Autowired
    JourneyService journeyService;
    @Autowired
    JourneyRepository journeyRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/{targetId}")
    public GetJourneyDto getJourney(
            @PathVariable(name = "targetId") String targetId
    ){
        Long journeyId = Long.parseLong(targetId);
        return journeyService.getJourney(journeyId);
    }

    @GetMapping("/myjourney/{ownerId}")
    public List<GetJourneyDto> getMyJourney(
            @PathVariable(name = "ownerId") String targetId
    ){
        Long ownerId = Long.parseLong(targetId);

        return journeyService.getMyJourneyList(ownerId , 0 , 20);
    }

    @GetMapping("/main/{locationEnum}/{userId}")
    public GetMainJourneyInfo getMainJourneyInformation(
            @PathVariable(name = "locationEnum") String locationEnumStr ,
            @PathVariable(name = "userId") String userId
    ){
        Location locEnum = Location.valueOf(locationEnumStr);
        int locationEnumInt = locEnum.getCode();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        String imageURLStr = "https://s3.ap-northeast-2.amazonaws.com/onemoon-empathy-s3/back/firstMainImg.jpg";
        String mainTextStr = "하루하루가\n모여 여정이 될 \n\n나의 오늘을\n기록해보세요";
        String booleanStr = "true";

        User owner = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(NotFoundException::new);
        Pageable paging  = new PageRequest( 0, 5, Sort.Direction.DESC, "jId");
        List<Journey> dbResult = journeyRepository.findAllByOwner(owner, paging);

        // 처음일때
        // https://s3.ap-northeast-2.amazonaws.com/onemoon-empathy-s3/back/firstMainImg.jpg
        // 처음이 아닐때 마지막 사진을 가져온다.
        if( dbResult.size() != 0 ){
            Journey temp = dbResult.get(0);
            booleanStr = "false";
            imageURLStr = temp.getImageUrl();
            String tempStr = TimeStampUtil.stampFormatSimple(temp.getCreationDate());
            String resultStr = tempStr.substring(0,7)+"\n"+tempStr.substring(8,10) + "\n" + temp.getTitle();
            mainTextStr = resultStr;
        }

        return GetMainJourneyInfo.builder()
                .otherPeopleList(journeyService.getJourneyByLocation(locationEnumInt , 0 , 5))
                .imageURL(imageURLStr)
                .enumStr(locEnum.getValue())
                .isFirst(booleanStr)
                .mainText(mainTextStr)
                .weekday(WeekDaysKor.getText(dayOfWeek-1))
                .build();
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
