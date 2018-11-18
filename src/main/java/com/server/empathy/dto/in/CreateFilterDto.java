package com.server.empathy.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
