package com.sparta.outsorcingproject.controller;

import com.sparta.outsorcingproject.dto.AdminSignupRequestDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import com.sparta.outsorcingproject.security.UserDetailsImpl;
import com.sparta.outsorcingproject.dto.LoginRequestDto;
import com.sparta.outsorcingproject.dto.SignupRequestDto;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.jwt.JwtUtil;
import com.sparta.outsorcingproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/user/admin")
    public String adminSignup(@RequestBody @Valid AdminSignupRequestDto requestDto) {
        userService.adminSignup(requestDto);

        return "관리자 회원가입 완료";
    }

    @PostMapping("/user/signup")
    public String signup(@RequestBody @Valid SignupRequestDto requestDto) {
        userService.signup(requestDto);

        return "회원가입 완료";
    }

    @PostMapping("/user/login")
    public String login(LoginRequestDto requestDto, HttpServletResponse res) {
        try {
            userService.login(requestDto, res);
        } catch (Exception e) {
            return "redirect:/api/user/login-page?error";
        }
        return "로그인 완료";
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
    public String refresh(@RequestHeader("RefreshToken") String refreshToken) {
        if (jwtUtil.validateToken(refreshToken)) {
            String username = jwtUtil.getUserInfoFromToken(refreshToken).getSubject();
            return jwtUtil.createAccessToken(username, userService.getUserByUsername(username).getRole());
        } else {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }
    }

    @DeleteMapping("/user/delete")
    public String userDelete(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        userService.deleteById(user.getId());
        //jwt토큰을 헤더에 넣어서 테스트
        return "회원탈퇴 완료";
    }
}