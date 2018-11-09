package com.server.empathy.service;

import com.server.empathy.dto.in.CreateFilterDto;
import com.server.empathy.entity.Filter;

import java.util.List;
import java.util.Map;

public interface FilterService {
    void addFilterType(String name);

    List<Filter> getFilterListByFilter(String filter);
    Map<String,List<Filter>> getAllFilter();
    void createFilter(CreateFilterDto dto);



}
