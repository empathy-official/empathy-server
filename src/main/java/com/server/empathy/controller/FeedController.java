package com.server.empathy.controller;

import com.server.empathy.entity.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/feed" , "/feed/"})
public class FeedController {

    // ********************************
    // TourAPI - Informatiojn
    // ********************************

    @Value("${tourapi.key}")
    private String apiKey;

//    @GetMapping("/")


    @GetMapping("/locenum")
    public List<Location> getLocationEnum(){
        List<Location> resultList = new ArrayList<>();
        for(Location loc : Location.values()){
            resultList.add(loc);
        }
        return resultList;
    }

    @GetMapping("/locchek/{check}")
    public Location check(
            @PathVariable(name = "check") String checkEnum
    ){
        return Location.valueOf(checkEnum);
    }


    @GetMapping("/tourapi/{mapX}/{mapY}/{range}")
    public void getLocationInfo(){
        System.out.println(apiKey);

    }

    private static String makeURL(String key, String mx,String my,String range , int numOfRow , int numOfPage){
        String resultUrl =
                "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?ServiceKey="
                + key + "&contentTypeId=&mapX=" + mx + "&mapY=" + my + "&radius=" + range
                + "&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows="+numOfRow+"&pageNo="
                + numOfPage ;

        return resultUrl;
    }


    // ********************************
    // Journey
    // ********************************

}
