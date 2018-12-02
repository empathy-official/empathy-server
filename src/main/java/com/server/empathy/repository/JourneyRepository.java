package com.server.empathy.repository;

import com.server.empathy.entity.Journey;
import com.server.empathy.entity.User;
import javassist.runtime.Desc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JourneyRepository extends CrudRepository<Journey,Long> {
//    Page<Journey> findByLocationCode(int locationCode , Pageable paging);
    List<Journey> findByLocationCodeOrderByJIdDesc(int locationCode );
//    Page<Journey> findByOwnerId(Long ownerId , Pageable paging);
    List<Journey> findAllByOwner(User owner , Pageable paging);

}
