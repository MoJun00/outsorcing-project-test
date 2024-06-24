package com.sparta.outsorcingproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsorcingproject.entity.Follower;
import com.sparta.outsorcingproject.entity.FollowerId;
import com.sparta.outsorcingproject.entity.User;

public interface FollowRepository extends JpaRepository<Follower, FollowerId> {
	Optional<Follower> findByFollower(User follower);
	Optional<Follower> findByFollowerAndMe(User follower, User me);
}
