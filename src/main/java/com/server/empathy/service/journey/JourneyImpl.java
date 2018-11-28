package com.server.empathy.service.journey;

import com.server.empathy.dto.in.journey.CreateJourneyDto;
import com.server.empathy.dto.in.journey.PatchJourneyInfoDto;
import com.server.empathy.dto.out.journey.GetJourneyDto;
import com.server.empathy.dto.out.journey.GetJourneySimpleDto;
import com.server.empathy.entity.Journey;
import com.server.empathy.entity.User;
import com.server.empathy.exception.BaseException;
import com.server.empathy.exception.NotFoundException;
import com.server.empathy.repository.JourneyRepository;
import com.server.empathy.repository.UserRepository;
import com.server.empathy.service.S3Uploader;
import com.server.empathy.service.TimeStampUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

@Service
public class JourneyImpl implements JourneyService{
    @Autowired
    S3Uploader s3Uploader;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JourneyRepository journeyRepository;

    @Override
    public void createJourney(CreateJourneyDto dto){
        String imageUrl;
        try{
            imageUrl = s3Uploader.upload(dto.getFile(),"journey");
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new BaseException("check s3 uploader");
        }
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(NotFoundException::new);

        Journey temp = Journey.builder()
                .title(dto.getTitle())
                .contents(dto.getContents())
                .imageUrl(imageUrl)
                .location(dto.getLocation())
                .locationCode(dto.getLocationEnum().getCode())
                .owner(owner)
                .build();
        journeyRepository.save(temp);

        owner.getJourneyList().add(temp);
        userRepository.save(owner);
    }

    @Override
    public GetJourneyDto getJourney(Long journeyId) {
        Journey temp = journeyRepository.findById(journeyId)
                .orElseThrow(NotFoundException::new);

        GetJourneyDto result = GetJourneyDto.builder()
                .journeyId(temp.getJId())
                .imageUrl(temp.getImageUrl())
                .ownerProfileUrl(temp.getOwner().getProfileUrl())
                .creationTime(TimeStampUtil.stampFormatSimple(temp.getCreationDate()))
                .title(temp.getTitle())
                .contents(temp.getContents())
                .location(temp.getLocation())
                .build();

        return result;
    }

    @Override
    public List<GetJourneyDto> getMyJourneyList(Long userId , int page , int size) {
        User owner = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        Pageable paging  = new PageRequest( page, size, Sort.Direction.DESC, "jId");
        List<Journey> dbResult = journeyRepository.findAllByOwner(owner, paging);
        List<GetJourneyDto> result = new ArrayList<>();
        dbResult.forEach( journey -> {
            result.add(
                    GetJourneyDto.builder()
                            .title(journey.getTitle())
                            .journeyId(journey.getJId())
                            .imageUrl(journey.getImageUrl())
                            .creationTime(TimeStampUtil.stampFormatSimple(journey.getCreationDate()))
                            .build()
            );
        });

        return result;
    }

    @Override
    public List<GetJourneySimpleDto> getJourneyByLocation(int locationCode , int page , int size) {
        // 역순으로 0페이지 5개씩
        Pageable paging  = new PageRequest(page, size, Sort.Direction.DESC, "jId");
        List<GetJourneySimpleDto> result = new ArrayList<>();
        if(journeyRepository.findByLocationCode(locationCode,paging).getSize() == 0) {
            throw new NotFoundException();
        }
        journeyRepository.findByLocationCode(locationCode,paging).forEach(journey -> {
           result.add(
                   GetJourneySimpleDto.builder()
                   .journeyId(journey.getJId())
                   .imageUrl(journey.getImageUrl())
                   .ownerName(journey.getOwner().getName())
                   .ownerProfileUrl(journey.getOwner().getProfileUrl())
                   .build()
           );
        });
        return result;
    }

    @Override
    public void updateJourneyInfo(PatchJourneyInfoDto dto) {
        Long journeyId = dto.getJourneyId();
        Journey temp = journeyRepository.findById(journeyId)
                .orElseThrow(NotFoundException::new);

        if(dto.getTitle() != null)
            temp.setTitle(dto.getTitle());
        if(dto.getContents() != null)
            temp.setContents(dto.getContents());

        journeyRepository.save(temp);
    }

    @Override
    public void updateJourneyImage(Long journeyId, MultipartFile file) {
        Journey temp = journeyRepository.findById(journeyId)
                .orElseThrow(NotFoundException::new);
        try {
            String newUrl = s3Uploader.upload(file , "journey");
            s3Uploader.deleteS3(temp.getImageUrl());
            temp.setImageUrl(newUrl);
            journeyRepository.save(temp);
        } catch(Exception e) {
            throw new BaseException(e.getMessage());
        }


    }

    @Override
    public void deleteJourney(Long journeyId) {
        Journey temp = journeyRepository.findById(journeyId)
                .orElseThrow(NotFoundException::new);
        s3Uploader.deleteS3(temp.getImageUrl());
//        temp.getOwner().getJourneyList().remove(temp);
        // owner save?

        // 이 전에 이미지 삭제 필요함 그리고 리스트에서도 빼야하나?
        journeyRepository.deleteById(journeyId);
    }
}
