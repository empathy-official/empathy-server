package com.server.empathy.controller;

import com.server.empathy.dto.in.CreateFilterDto;
import com.server.empathy.entity.Filter;
import com.server.empathy.exception.BaseException;
import com.server.empathy.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({ "/filter/" , "/filter" })
public class FilterController {

    @Autowired
    FilterService filterService;

    @GetMapping("/")
    public Map<String,List<Filter>> getAllFilter(){
        return filterService.getAllFilter();
    }

    // filter 를 만들때는 먼저 묶이는 종류 type을 먼저 만들어야 한다.
    // json 이 아닌 String 으로 name을 보내자
    @PostMapping("/type")
    public void createType(
            @RequestBody String name
    ){
        filterService.addFilterType(name);
    }

    // 그 후 있는 filterListName에 대해서 filter를 만든다.
    // 사실 순서가 바뀌어도 상관은 없다.
    @PostMapping("/")
    public void createFilter(
            @Valid @RequestBody CreateFilterDto dto ,
            BindingResult result
    ){
        if(result.hasErrors()) throw new BaseException();
        filterService.createFilter(dto);

    }
}
