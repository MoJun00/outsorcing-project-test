package com.sparta.outsorcingproject.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.outsorcingproject.dto.AdminSignupRequestDto;
import com.sparta.outsorcingproject.dto.LoginRequestDto;
import com.sparta.outsorcingproject.dto.SignupReponseDto;
import com.sparta.outsorcingproject.dto.SignupRequestDto;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.jwt.JwtUtil;
import com.sparta.outsorcingproject.security.UserDetailsImpl;
import com.sparta.outsorcingproject.service.KakaoService;
import com.sparta.outsorcingproject.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
public class UserController {

	private final JwtUtil jwtUtil;
	private final UserService userService;
	private final KakaoService kakaoService;

	public UserController(UserService userService, JwtUtil jwtUtil, KakaoService kakaoService) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.kakaoService = kakaoService;
	}

	@PostMapping("/user/admin")
	public ResponseEntity<SignupReponseDto> adminSignup(@RequestBody @Valid AdminSignupRequestDto requestDto) {

		return userService.adminSignup(requestDto);

	}

	@PostMapping("/user/signup")
	public ResponseEntity<SignupReponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto) {

		return userService.signup(requestDto);

	}

	@PostMapping("/user/login")
	public ResponseEntity<String> login(LoginRequestDto requestDto, HttpServletResponse res) {

		return userService.login(requestDto, res);

	}

	@PostMapping("/user/logout")
	public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
		// 현재 사용자의 세션 무효화
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		// 쿠키 삭제
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}

		return ResponseEntity.ok("로그아웃 완료");
	}

	@PostMapping("/user/refresh")
	public ResponseEntity<String> refresh(@RequestHeader("RefreshToken") String refreshToken) {
		if (jwtUtil.validateToken(refreshToken)) {
			String username = jwtUtil.getUserInfoFromToken(refreshToken).getSubject();
			return ResponseEntity.ok(jwtUtil.createAccessToken(username, userService.getUserByUsername(username).getRole()));
		} else {
			throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
		}
	}

	@DeleteMapping("/user/delete")
	public ResponseEntity<String> userDelete(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String password) {

		User user = userDetails.getUser();

		return userService.deleteById(user.getId(), password);
		//jwt토큰을 헤더에 넣어서 테스트
	}

	@GetMapping("/kakao")
	public ResponseEntity<String> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {

		String token = kakaoService.kakaoLogin(code).substring(7);

		Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
		cookie.setPath("/");
		response.addCookie(cookie);

		return ResponseEntity.ok().body("token " + token);
	}
}