package com.server.empathy.controller;

import com.server.empathy.dto.out.info.GetTourAPIDetailDto;
import com.server.empathy.dto.out.info.GetTourAPIItem;
import com.server.empathy.dto.out.info.InfoAllianceDetailDto;
import com.server.empathy.dto.out.info.InfoAllianceDto;
import com.server.empathy.entity.Alliance;
import com.server.empathy.exception.BaseException;
import com.server.empathy.repository.AllianceRepository;
import com.server.empathy.service.S3Uploader;
import org.apache.http.entity.ContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/info" , "/info/"})
public class ImformationController {
    @Value("${tourapi.key}")
    private String apiKey;

    // 관광지 > contentTypeId=12
    // 문화시설 > contentTypeId=14
    // 측제공연행사 > contentTypeId=15
    // 음식점 >  contentTypeId=39
    @GetMapping("/tourAPI/{contentType}/{mapX}/{mapY}/{range}/{pageNumber}")
    public List<GetTourAPIItem> getTourAPI(
            @PathVariable(name = "contentType") String contentType ,
            @PathVariable(name = "mapX") String mapX ,
            @PathVariable(name = "mapY") String mapY ,
            @PathVariable(name = "range") String range ,
            @PathVariable(name = "pageNumber") String pageNumber
    ){

        String targetURL = makeURL(apiKey, 12,
                "126.981106", "37.568477" , "1000" ,
                10,1);

        List<GetTourAPIItem> result = new ArrayList<>();

        try {
            Document document = Jsoup.connect(targetURL).get();
            Elements elements = document.select("item");
            int eleSize = elements.size();
            for(int i=0;i<eleSize;i++){
                result.add(
                        GetTourAPIItem.builder()
                                .imageURL(elements.get(i).select("firstimage").text())
                                .title(elements.get(i).select("title").text())
                                .addr(elements.get(i).select("addr1").text())
                                .targetId(elements.get(i).select("contentid").text())
                                .build()
                );

            }
        } catch ( Exception e ) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    @GetMapping("/tourAPI/detail/{contentType}/{targetId}")
    public GetTourAPIDetailDto getTourAPIDetail(
            @PathVariable(name = "contentType") String contentType ,
            @PathVariable(name = "targetId") String targetId
    ){

        String imageURL = ""; // default url
        String title;//
        String overviewText;//
        String businessHours;
        String dayOff;
        String creditCard; //
        String withPet;//
        String locationStr;//
        String mapx,mapy;

        GetTourAPIDetailDto result = GetTourAPIDetailDto.builder().build();

        String targetURL;
        try {
            targetURL = makeDetailURL(apiKey,1,contentType,targetId);
            Document document = Jsoup.connect(targetURL).get();
            Elements tempElement = document.select("item");
            title = tempElement.get(0).select("title").text();
            locationStr = tempElement.get(0).select("addr1").text();
            imageURL = tempElement.get(0).select("firstimage").text().equals("") ? "https://s3.ap-northeast-2.amazonaws.com/onemoon-empathy-s3/back/detailBase.jpg" : tempElement.get(0).select("firstimage").text();

            overviewText = tempElement.get(0).select("overview").text();
            mapx = tempElement.get(0).select("mapx").text();
            mapy = tempElement.get(0).select("mapy").text();

            targetURL = makeDetailURL(apiKey,2,contentType,targetId);
            document = Jsoup.connect(targetURL).get();
            tempElement = document.select("item");
//            // 관광지 -> 카드 , 펫
            creditCard = tempElement.get(0).select("chkcreditcard").text();
            withPet = tempElement.get(0).select("chkpet").text();
//            // 문화 -> 이용시간 , 펫 , 카드
//
//            // food -> 영업시간 쉬는날 카드
            businessHours = tempElement.get(0).select("opentimefood").text();
            dayOff = tempElement.get(0).select("restdatefood").text();
//            // 축제 공연시간 무료


            result = GetTourAPIDetailDto.builder()
                    .imageURL(imageURL)
                    .title(title)
                    .locationStr(locationStr)
                    .overviewText(overviewText)
                    .businessHours(businessHours)
                    .dayOff(dayOff)
                    .withPet(withPet)
                    .creditCard(creditCard)
                    .mapx(mapx)
                    .mapy(mapy)
                    .build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;

    }



    @GetMapping("/alliance")
    public InfoAllianceDto getAllianceInfo() {
        return InfoAllianceDto.builder()
                .imageURL("https://s3.ap-northeast-2.amazonaws.com/onemoon-empathy-s3/back/singer.jpg")
                .kind("인디밴드")
                .locatiionStr("서울시 성북구 석관동\n화랑로 30길-17")
                .name("Ra'mie")
                .targetId("1") // 임시 값
                .build();
    }

    @GetMapping("/alliance/detail/{targetId}")
    public InfoAllianceDetailDto getAllianceDetail(
            @PathVariable(name = "targetId") String targetId
    ) {
        return InfoAllianceDetailDto.builder()
                .imageURL("https://s3.ap-northeast-2.amazonaws.com/onemoon-empathy-s3/back/singer.jpg")
                .title("퍼플버거")
                .overview("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s")
                .duration("11.10-11.31")
                .playTime("13:00~14:30")
                .priceInfo("평일-13,000원\n주말-15,000원")
                .locationStr("서울시 성북구 석관동 화랑로 30길-17")
                .mapx("126.981106")
                .mapy("37.568477")
                .build();
    }

    private static String makeURL(String key, int type ,
                                  String mx, String my, String range,
                                  int numOfRow, int numOfPage){
        String resultUrl =
                "http://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?ServiceKey="
                        + key + "&contentTypeId="+type
                        +"&mapX=" + mx + "&mapY=" + my + "&radius=" + range
                        + "&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows="+numOfRow+"&pageNo="
                        + numOfPage;

        return resultUrl;
    }
    private static String makeDetailURL(String key, int type , String contentType , String targetId){
        String first = "";
        String second = "";

        switch (type) {
            case 1: // Common
                first = "detailCommon";
                second = "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";
                break;
            case 2: // Intro
                first = "detailIntro";
                second = "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y";
                break;
            case 3: // Info
                first = "detailInfo";
                second = "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y";
                break;
            default:
                break;
        }
        String result = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/" + first +  "?ServiceKey=" + key + "&contentTypeId=" + contentType
                + "&contentId=" + targetId + second;

        return result;
    }

    @Autowired
    AllianceRepository allianceRepository;
    @Autowired
    S3Uploader s3Uploader;

    @PostMapping("/alliance")
    public void craeteAlliance(
            @RequestParam(value = "file") MultipartFile file,
//            @RequestParam(value = "title") String title ,
//            @RequestParam(value = "subTitle") String subTitle ,
//            @RequestParam(value = "locationStr") String locationStr ,
//            @RequestParam(value = "introduce") String introduce ,
//            @RequestParam(value = "dayTime") String dayTime ,
//            @RequestParam(value = "hourTime") String hourTime ,
//            @RequestParam(value = "priceInfo") String priceInfo
            @RequestPart(value = "title") String title ,
            @RequestPart(value = "subTitle") String subTitle ,
            @RequestPart(value = "locationStr") String locationStr ,
            @RequestPart(value = "introduce") String introduce ,
            @RequestPart(value = "dayTime") String dayTime ,
            @RequestPart(value = "hourTime") String hourTime ,
            @RequestPart(value = "priceInfo") String priceInfo
    ){
        try {
            System.out.println(title);
            System.out.println(subTitle);

            String newUrl = s3Uploader.upload(file , "alliance");
            Alliance temp = Alliance.builder()
                    .imageURL(newUrl)
                    .title(title)
                    .subTitle(subTitle)
                    .locationStr(locationStr)
                    .introduce(introduce.replaceAll("(\r\n|\r|\n|\n\r)", "\n"))
                    .dayTime(dayTime.replaceAll("(\r\n|\r|\n|\n\r)", "\n"))
                    .hourTime(hourTime.replaceAll("(\r\n|\r|\n|\n\r)", "\n"))
                    .priceInfo(priceInfo.replaceAll("(\r\n|\r|\n|\n\r)", "\n"))
                    .build();
            allianceRepository.save(temp);
        } catch(Exception e) {
            throw new BaseException(e.getMessage());
        }
    }





    // common 만 약간 양식이 다르다.
    // http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?ServiceKey=인증키&contentTypeId=12&contentId=2373204&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y
    // http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?ServiceKey=인증키&contentTypeId=12&contentId=2373204&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y
    // http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo?ServiceKey=인증키&contentTypeId=12&contentId=2373204&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&listYN=Y
}
