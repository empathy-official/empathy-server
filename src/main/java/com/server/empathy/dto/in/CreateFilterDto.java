package com.server.empathy.dto.in;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class CreateFilterDto {
    @NotNull
    private String type;
    @NotNull
    private String name;
//    @NotNull
    private String standard;
//    @NotNull
    private String alignLeft;
//    @NotNull
    private String gravity;

//    private String imageURL;
}
