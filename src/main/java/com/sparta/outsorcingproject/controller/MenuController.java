package com.sparta.outsorcingproject.controller;

import com.sparta.outsorcingproject.dto.MenuRequestDto;
import com.sparta.outsorcingproject.dto.MenuResponseDto;
import com.sparta.outsorcingproject.security.UserDetailsImpl;
import com.sparta.outsorcingproject.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class MenuController {

    private final MenuService menuService;

    //메뉴 등록
    @PostMapping("/{storeId}/menu")
    public ResponseEntity<String> createMenu(@PathVariable long storeId, @RequestBody MenuRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String response = menuService.createMenu(storeId, requestDto,userDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //메뉴 조회
    @GetMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable long storeId, @PathVariable long menuId) {
        MenuResponseDto response = menuService.getMenu(storeId,menuId);

        return ResponseEntity.ok().body(response);
    }

    //메뉴 수정
    @PatchMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<String> updateMenu(@PathVariable long storeId, @PathVariable long menuId, @RequestBody MenuRequestDto requestDto,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String response = menuService.updateMenu(storeId,menuId,requestDto,userDetails);

        return ResponseEntity.ok().body(response);
    }

    //메뉴 삭제
    @DeleteMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable long storeId, @PathVariable long menuId,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String response = menuService.deleteMenu(storeId,menuId,userDetails);

        return ResponseEntity.ok().body(response);
    }

    //가게 메뉴들 조회
    @GetMapping("/{storeId}/menus")
    public ResponseEntity<List<MenuResponseDto>> getMenus(@PathVariable long storeId) {
        List<MenuResponseDto> response = menuService.getMenus(storeId);

        return ResponseEntity.ok().body(response);
    }

}
