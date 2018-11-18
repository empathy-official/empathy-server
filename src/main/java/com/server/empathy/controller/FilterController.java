package com.server.empathy.controller;

import com.server.empathy.dto.in.CreateFilterDto;
import com.server.empathy.dto.in.UpdateFilterImageDto;
import com.server.empathy.dto.in.UpdateFilterInfoDto;
import com.server.empathy.dto.out.FilterListDto;
import com.server.empathy.entity.FilterType;
import com.server.empathy.exception.BaseException;
import com.server.empathy.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping({ "/filter/" , "/filter" })
public class FilterController {

    @Autowired
    FilterService filterService;

    // ***************
    // Filter Type
    // ***************
    // filter 를 만들때는 먼저 묶이는 종류 type을 먼저 만들어야 한다.
    // json 이 아닌 String 으로 name을 보내자
    @PostMapping("/type")
    public void createType(
            @RequestBody String name
    ){
        filterService.addFilterType(name);
    }

    @GetMapping("/type")
    public List<FilterType> getType(){
        return filterService.getAllFilterType();
    }

    @PatchMapping("/type")
    public void patchtype(FilterType type){
        filterService.patchFilterTypeItem(type);
    }

    @DeleteMapping("/type")
    public void deleteType(Long filterTypeId){
        filterService.deleteFilterListItem(filterTypeId);
    }

    // ***************
    // Filter
    // ***************
    @GetMapping("/")
    public FilterListDto getAllFilter(){ return filterService.getAllFilter(); }
    @GetMapping("/pose")
    public FilterListDto getPoseFilter(){ return filterService.getFilterByType("pose"); }
    @GetMapping("/origin")
    public FilterListDto getFilter(){ return filterService.getFilterByType("original"); }

    // 그 후 있는 filterListName에 대해서 filter를 만든다.
    // 사실 순서가 바뀌어도 상관은 없다.
    @PostMapping("/")
    public void createFilter(
            @Valid @RequestBody CreateFilterDto dto ,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) throw new BaseException();
        filterService.createFilter(dto);

    }

    // update Filter
    // image 도 한번에 바꿀수 있는지 확인해야함 -> 안된다 이미지 따로
    @PatchMapping("/info")
    public void patchFilterInfo(
            @Valid @RequestBody UpdateFilterInfoDto dto ,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()) throw new BaseException();
        filterService.updateFilterInfo(dto);
    }

    // update Filter Image
    @PatchMapping("/iamge")
    public void patchFilterImage(
            @RequestParam("targetFilterId") Long targetId ,
            @RequestParam("image") MultipartFile multipartFile
    ){
        filterService.updateFilterImage(
                UpdateFilterImageDto.builder()
                        .image(multipartFile)
                        .targetFilterId(targetId)
                        .build()
        );
    }

    @DeleteMapping("/")
    public void deletefilter(Long filterId){
        filterService.deleteFilter(filterId);
    }
}
