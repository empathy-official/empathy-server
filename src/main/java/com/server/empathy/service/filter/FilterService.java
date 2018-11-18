package com.server.empathy.service.filter;

import com.server.empathy.dto.in.filter.CreateFilterDto;
import com.server.empathy.dto.in.filter.UpdateFilterImageDto;
import com.server.empathy.dto.in.filter.UpdateFilterInfoDto;
import com.server.empathy.dto.out.filter.FilterListDto;
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
    FilterListDto getFilterByType(String type);
    void createFilter(CreateFilterDto dto);
    void updateFilterInfo(UpdateFilterInfoDto dto);
    void updateFilterImage(UpdateFilterImageDto dto);
    void deleteFilter(Long filterId);


}
