package com.server.empathy.service;

import com.server.empathy.dto.in.CreateFilterDto;
import com.server.empathy.dto.in.UpdateFilterImageDto;
import com.server.empathy.dto.in.UpdateFilterInfoDto;
import com.server.empathy.dto.out.FilterListDto;
import com.server.empathy.dto.out.FilterListEachDto;
import com.server.empathy.entity.Filter;
import com.server.empathy.entity.FilterList;
import com.server.empathy.exception.NotFoundException;
import com.server.empathy.repository.FilterListRepository;
import com.server.empathy.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FilterImpl implements FilterService{

    @Autowired
    S3Uploader s3Uploader;
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
    public FilterListDto getAllFilter() {

        Map<String,List<Filter>> filterMap = new HashMap<>();
        List<FilterListEachDto> result = new ArrayList<>();

        filterListRepository.findAll().forEach(filterList->{
            List<Filter> temp = new ArrayList<>();
            filterMap.put(filterList.getFilterName() , temp);

        });

        filterRepository.findAll().forEach(filter -> {
            filterMap.get(filter.getType()).add(filter);

        });

//        return filterMap;

        for(Map.Entry<String,List<Filter>> entry : filterMap.entrySet()){
            result.add(
                    FilterListEachDto.builder()
                            .filterType(entry.getKey())
                            .filters(entry.getValue())
                            .build()
            );
        }

        return FilterListDto.builder().filterList(result).build();
    }


    @Override
    public void createFilter(CreateFilterDto dto) {

        Filter filt = Filter.builder()
                .type(dto.getType())
                .name(dto.getName())
                .standard(dto.getStandard())
                .gravity(dto.getGravity())
                .alignLeft(dto.getAlignLeft().toLowerCase().equals("true"))
                .build();

        filterRepository.save(filt);
    }

    @Override
    public void updateFilterInfo(UpdateFilterInfoDto dto) {
//        Optional<Filter> check  = filterRepository.findById(dto.getTargetFilterId());
//        Filter target;
//        if(check.isPresent()) target = check.get();
//        else throw new NotFoundException();

        Filter target = filterRepository.findById(dto.getTargetFilterId())
                .orElseThrow(() -> new NotFoundException());

        if(dto.getAlignLeft() != null) {
            boolean val = dto.getAlignLeft().equals("true")? true : false;
            target.setAlignLeft(val);
        }
        if(dto.getStandard() != null) { target.setStandard(dto.getStandard()); }
        if(dto.getGravity() != null) { target.setGravity(dto.getGravity()); }
        if(dto.getName() != null) { target.setName(dto.getName()); }

        filterRepository.save(target);
    }

    @Override
    public void updateFilterImage(UpdateFilterImageDto dto) {
        Filter target = filterRepository.findById(dto.getTargetFilterId())
                .orElseThrow(() -> new NotFoundException());

        if(target.getImageURL() == null){
            String firstUrl;
            try {
                firstUrl = s3Uploader.upload(dto.getImage(),"filter");
                target.setImageURL(firstUrl);
                filterRepository.save(target);
            } catch (Exception e) { System.out.println(e); }
            return;
        }

        String newUrl;
        try {
            s3Uploader.deleteS3(target.getImageURL());
            newUrl = s3Uploader.upload(dto.getImage(),"filter");
            target.setImageURL(newUrl);
            filterRepository.save(target);
        }catch (Exception e) { System.out.println(e); }
    }


}
