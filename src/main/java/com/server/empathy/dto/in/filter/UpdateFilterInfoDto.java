package com.server.empathy.dto.in.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateFilterInfoDto {
    @NotNull
    private Long targetFilterId;
    private String name;
    private String standard;
    private String gravity;
    private String alignLeft;
}
