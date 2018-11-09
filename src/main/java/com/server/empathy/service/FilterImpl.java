package com.server.empathy.service;

import com.server.empathy.dto.in.CreateFilterDto;
import com.server.empathy.entity.Filter;
import com.server.empathy.entity.FilterList;
import com.server.empathy.repository.FilterListRepository;
import com.server.empathy.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilterImpl implements FilterService{

    @Autowired
    FilterRepository filterRepository;
    @Autowired
    FilterListRepository filterListRepository;

    @Override
    public void addFilterType(String name) {
        filterListRepository.save(FilterList.builder().filterName(name).build());
    }

    @Override
    public List<Filter> getFilterListByFilter(String filter) {
        return filterRepository.findFilterByType(filter);
    }

    @Override
    public Map<String,List<Filter>> getAllFilter() {

        Map<String,List<Filter>> filterMap = new HashMap<>();

        filterListRepository.findAll().forEach(filterList->{
            List<Filter> temp = new ArrayList<>();
            filterMap.put(filterList.getFilterName() , temp);
        });

        filterRepository.findAll().forEach(filter -> {
            filterMap.get(filter.getType()).add(filter);
        });

        return filterMap;
    }


    @Override
    public void createFilter(CreateFilterDto dto) {

        Filter filt = Filter.builder()
                .type(dto.getType())
                .name(dto.getName())
                .standard(dto.getStandard())
                .gravity(dto.getGravity())
                .alingLeft(dto.getAlignLeft().toLowerCase().equals("true"))
                .build();

        filterRepository.save(filt);
    }
}
