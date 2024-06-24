package com.sparta.outsorcingproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsorcingproject.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	//signup
	Optional<User> findByUsername(String username);

	Optional<User> findByKakaoId(Long kakaoId);
}
