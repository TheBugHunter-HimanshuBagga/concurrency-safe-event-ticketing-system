package com.himanshu.event_ticketing_system.repository;

import com.himanshu.event_ticketing_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {

    Optional<User> findByEmail(String email); // optional helps handle the absence cases successfully

}
