package com.server.empathy.service;

import com.server.empathy.dto.in.CreateFilterDto;
import com.server.empathy.dto.in.UpdateFilterImageDto;
import com.server.empathy.dto.in.UpdateFilterInfoDto;
import com.server.empathy.dto.out.FilterListDto;
import com.server.empathy.entity.Filter;

import java.util.List;

public interface FilterService {
    void addFilterType(String name);

    List<Filter> getFilterListByFilter(String filter);
    FilterListDto getAllFilter();
    void createFilter(CreateFilterDto dto);
    void updateFilterInfo(UpdateFilterInfoDto dto);
    void updateFilterImage(UpdateFilterImageDto dto);


}
