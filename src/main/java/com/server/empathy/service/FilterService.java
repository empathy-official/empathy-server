package com.server.empathy.service;

import com.server.empathy.dto.in.CreateFilterDto;
import com.server.empathy.dto.in.UpdateFilterImageDto;
import com.server.empathy.dto.in.UpdateFilterInfoDto;
import com.server.empathy.dto.out.FilterListDto;
import com.server.empathy.entity.Filter;
import com.server.empathy.entity.FilterType;

import java.util.List;

public interface FilterService {

    List<Filter> getFilterListByFilter(String filter);
    // ***************
    // Filter Type
    // ***************
    List<FilterType> getAllFilterType();
    void addFilterType(String name);
    void patchFilterTypeItem(FilterType item);
    void deleteFilterListItem(Long filterTypeId);
    // ***************
    // Filter
    // ***************
    FilterListDto getAllFilter();
    void createFilter(CreateFilterDto dto);
    void updateFilterInfo(UpdateFilterInfoDto dto);
    void updateFilterImage(UpdateFilterImageDto dto);
    void deleteFilter(Long filterId);


}
