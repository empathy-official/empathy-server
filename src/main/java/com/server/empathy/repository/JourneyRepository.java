package com.server.empathy.repository;

import com.server.empathy.entity.Journey;
import com.server.empathy.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface JourneyRepository extends CrudRepository<Journey,Long> {
    Page<Journey> findByLocationCode(int locationCode , Pageable paging);
//    Page<Journey> findByOwner(User owner);

}
