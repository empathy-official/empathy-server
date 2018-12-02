package com.server.empathy.dto.out.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTourAPIItem2 {
    private String imageURL;
    private String title;
    private String addr;
    private String targetId;
    private String contentType;
}
