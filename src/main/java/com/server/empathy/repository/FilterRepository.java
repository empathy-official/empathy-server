package com.server.empathy.repository;

import com.server.empathy.entity.Filter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FilterRepository extends CrudRepository<Filter,Long> {
    List<Filter> findFilterByType(String type);
}
