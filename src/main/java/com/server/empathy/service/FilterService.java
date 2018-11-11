package com.server.empathy.service;

import com.server.empathy.dto.in.CreateFilterDto;
import com.server.empathy.dto.out.FilterListDto;
import com.server.empathy.entity.Filter;

import java.util.List;
import java.util.Map;

public interface FilterService {
    void addFilterType(String name);

    List<Filter> getFilterListByFilter(String filter);
    FilterListDto getAllFilter();
    void createFilter(CreateFilterDto dto);



}
