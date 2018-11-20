package com.server.empathy.dto.in.journey;

import com.server.empathy.entity.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateJourneyDto {
    private Long ownerId;
    private String title;
    private String contents;
    private String location;
    private Location locationEnum;
//    private String imageUrl;
    private MultipartFile file;


}
