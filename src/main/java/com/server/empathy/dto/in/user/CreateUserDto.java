package com.server.empathy.dto.in.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateUserDto {
    @NotNull
    private String name;
//    @NotNull
    private String loginApi;
    private String profileUrl;
    private String appUserId;
}
