package com.sparta.outsorcingproject.repository;

import com.sparta.outsorcingproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //signup
    Optional<User> findByUsername(String username);

}
