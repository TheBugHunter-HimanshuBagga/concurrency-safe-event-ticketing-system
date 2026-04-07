package com.himanshu.event_ticketing_system.repository;

import com.himanshu.event_ticketing_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {

}
