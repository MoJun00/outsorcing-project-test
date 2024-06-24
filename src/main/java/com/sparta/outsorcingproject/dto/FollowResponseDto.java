package com.sparta.outsorcingproject.dto;

import com.sparta.outsorcingproject.entity.Follower;

import lombok.Getter;

@Getter
public class FollowResponseDto {

	private final long followerId;
	private final long meId;

	public FollowResponseDto(Follower follower) {
		followerId = follower.getFollower().getId();
		meId = follower.getMe().getId();
	}
}
