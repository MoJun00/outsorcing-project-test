package com.sparta.outsorcingproject.admin;

import com.sparta.outsorcingproject.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    //유저 전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<UserResponseDto> allUsers = adminService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    //사용자 권한 수정 삭제
    @PutMapping("/user/{userId}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable Long userId, @RequestParam String role,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok("권한 수정 되었습니다");
    }

    //메뉴 전체 조회

    //주문 전체 조회

    //리뷰 전체 조회

    //메뉴 생성

    //메뉴수정

    //메뉴 삭제

}
