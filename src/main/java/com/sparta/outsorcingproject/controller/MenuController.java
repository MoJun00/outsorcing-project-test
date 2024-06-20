package com.sparta.outsorcingproject.controller;

import com.sparta.outsorcingproject.dto.MenuRequestDto;
import com.sparta.outsorcingproject.dto.MenuResponseDto;
import com.sparta.outsorcingproject.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{storeId}/menu")
    public ResponseEntity<String> createMenu(@PathVariable long storeId, @RequestBody MenuRequestDto requestDto) {
        String response = menuService.createMenu(storeId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<MenuResponseDto> getMenu(@PathVariable long storeId, @PathVariable long menuId) {
        MenuResponseDto response = menuService.getMenu(storeId,menuId);

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<String> updateMenu(@PathVariable long storeId, @PathVariable long menuId, @RequestBody MenuRequestDto requestDto) {
        String response = menuService.updateMenu(storeId,menuId,requestDto);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{storeId}/menu/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable long storeId, @PathVariable long menuId) {
        String response = menuService.deleteMenu(storeId,menuId);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{storeId}/menus")
    public ResponseEntity<List<MenuResponseDto>> getMenus(@PathVariable long storeId) {
        List<MenuResponseDto> response = menuService.getMenus(storeId);

        return ResponseEntity.ok().body(response);
    }

}
