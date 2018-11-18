package com.server.empathy.dto.out.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetUserDto {
    private Long userId;
    private String name;
    private String loginApi;
    private String profileUrl;
    private String creationTime;
    // journey list
    //
}
