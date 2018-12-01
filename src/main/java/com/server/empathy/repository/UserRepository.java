package com.server.empathy.repository;

import com.server.empathy.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByAppUserId(String userId);
}
