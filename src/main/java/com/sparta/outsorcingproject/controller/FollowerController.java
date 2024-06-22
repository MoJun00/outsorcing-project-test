package com.sparta.outsorcingproject.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsorcingproject.dto.FollowResponseDto;
import com.sparta.outsorcingproject.security.UserDetailsImpl;
import com.sparta.outsorcingproject.service.FollowService;

import lombok.RequiredArgsConstructor;

@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowerController {

	private final FollowService followService;

	@PostMapping("/{followerId}")
	public ResponseEntity<FollowResponseDto> follow(
		@PathVariable long followerId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		FollowResponseDto responseDto = followService.follow(followerId, userDetails.getUser().getId());

		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}
}
