package com.server.empathy.dto.out;

import com.server.empathy.entity.Filter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterListEachDto {
    private String filterType;
    private List<Filter> filters;
}
