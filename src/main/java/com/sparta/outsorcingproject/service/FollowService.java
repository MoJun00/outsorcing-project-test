package com.sparta.outsorcingproject.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.outsorcingproject.dto.FollowResponseDto;
import com.sparta.outsorcingproject.entity.Follower;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.repository.FollowRepository;
import com.sparta.outsorcingproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {

	private final FollowRepository followRepository;
	private final UserRepository userRepository;
	private final MessageSource messageSource;

	@Transactional
	public FollowResponseDto follow(long followerId, long meId) {

		if (followerId == meId) {
			throw new IllegalArgumentException(
				messageSource.getMessage("follow.mismatch.user", null, Locale.getDefault())
			);
		}

		User follower = userRepository.findById(followerId).orElseThrow(
			() -> new IllegalArgumentException(
				messageSource.getMessage("not.find.user", null, Locale.getDefault())
			)
		);

		if (followRepository.findByFollower(follower).isPresent()) {
			throw new IllegalArgumentException(
				messageSource.getMessage("duplication.follow", null, Locale.getDefault())
			);
		}

		User meUser = userRepository.findById(meId).orElseThrow(
			() -> new IllegalArgumentException(
				messageSource.getMessage("not.find.user", null, Locale.getDefault())
			)
		);

		Follower savedFollower = followRepository.save(new Follower(follower, meUser));

		return new FollowResponseDto(savedFollower);

	}

	public void unfollow(long followerId, long meId) {

		User follower = userRepository.findById(followerId).orElseThrow(
			() -> new IllegalArgumentException(
				messageSource.getMessage("not.find.user", null, Locale.getDefault())
			)
		);

		User meUser = userRepository.findById(meId).orElseThrow(
			() -> new IllegalArgumentException(
				messageSource.getMessage("not.find.user", null, Locale.getDefault())
			)
		);

		Follower findFollower = followRepository.findByFollowerAndMe(follower, meUser).orElseThrow(
			() -> new IllegalArgumentException(
				messageSource.getMessage("not.find.follow", null, Locale.getDefault())
			)
		);

		followRepository.delete(findFollower);
	}
}
