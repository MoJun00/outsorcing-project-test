package com.sparta.outsorcingproject.admin;

import com.sparta.outsorcingproject.dto.MenuRequestDto;
import com.sparta.outsorcingproject.dto.MenuResponseDto;
import com.sparta.outsorcingproject.dto.OrdersResponseDto;
import com.sparta.outsorcingproject.dto.ReviewResponseDto;
import com.sparta.outsorcingproject.entity.Review;
import com.sparta.outsorcingproject.security.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> allUser = adminService.getAllUsers();
        return ResponseEntity.ok(allUser);
    }

    //사용자 권한 수정 삭제
    @PutMapping("/user/{userId}/role")
    public ResponseEntity<String> updateUserRole(@PathVariable Long userId, @RequestParam String newRole,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {

        adminService.updateUserRole(userId, newRole, userDetails.getUser());
        return ResponseEntity.ok(userId + "유저의 권한이 " + newRole + " 으로 수정되었습니다.");
    }

    //특정 회원 정보 수정
    @PutMapping("/user/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId, @Valid @RequestBody UpdateUserRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {

        adminService.updateUser(userId,requestDto);
        return ResponseEntity.ok(userDetails.getUser().getUsername() + " 님에의해 " + userId + "번 회원님의 정보가 수정되었습니다");

    }

    //특정 회원 삭제
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok(userDetails.getUser().getUsername() + " 님에의해 " + userId + "번 회원님이 삭제되었습니다.");
    }

    //특정 회원 차단
    @PutMapping("/user/block/{userId}")
    public ResponseEntity<String> blockUser(@PathVariable Long userId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        adminService.blockUser(userId);
        return ResponseEntity.ok(userDetails.getUser().getUsername() + " 님에의해 " + userId + "번 회원님이 차단되었습니다.");
    }

    //메뉴 전체 조회
    @GetMapping("/menus")
    public ResponseEntity<List<MenuResponseDto>> getAllMenus() {
        List<MenuResponseDto> allMenu = adminService.getAllMemus();
        return ResponseEntity.ok(allMenu);
    }

    //주문 전체 조회
    @GetMapping("/orders")
    public ResponseEntity<List<OrdersResponseDto>> getAllOrders() {
        List<OrdersResponseDto> allOder = adminService.getAllOrders();
        return ResponseEntity.ok(allOder);
    }

    //리뷰 전체 조회
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        List<ReviewResponseDto> allReview = adminService.getAllReviews();
        return ResponseEntity.ok(allReview);
    }

    //메뉴 생성
    @PostMapping("/{storeId}/menu")
    public ResponseEntity<String> createMenu(@PathVariable long storeId, @RequestBody MenuRequestDto requestDto) {
        String response = adminService.createMenu(storeId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //메뉴수정
    @PutMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<String> updateMenu(@PathVariable long storeId, @PathVariable long menuId, @RequestBody MenuRequestDto requestDto) {
        String response = adminService.updateMenu(storeId,menuId,requestDto);

        return ResponseEntity.ok().body(response);
    }

    //메뉴 삭제
    @DeleteMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable long storeId, @PathVariable long menuId) {
        String str = adminService.deleteMenu(storeId,menuId);

        return ResponseEntity.ok().body(str);
    }
}
