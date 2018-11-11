package com.server.empathy.dto.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateFilterImageDto {
    @NotNull
    private Long targetFilterId;
    @NotNull
    private MultipartFile image;

}
