package com.sparta.outsorcingproject.controller;

import com.sparta.outsorcingproject.dto.StoreRequestDto;
import com.sparta.outsorcingproject.dto.StoreResponseDto;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.service.StoreService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 가게 등록
    // UserDetails 구현 후 사용
    @PostMapping
    public ResponseEntity<String> createStore(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody StoreRequestDto requestDto
    ){
        return storeService.createStore(userDetails, requestDto);
    }

    // 가게 전체 조회
    @GetMapping
    public ResponseEntity <List<StoreResponseDto>> findAll(){
        return storeService.findAll();
    }

    // 가게 단일 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> findOne(@PathVariable Long storeId){
        return storeService.findOne(storeId);
    }

    // 가게 수정
    @PutMapping("/{storeId}")
    public ResponseEntity<String> updateStore(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody StoreRequestDto storeRequestDto,
            @PathVariable Long storeId
    ){
        return storeService.updateStore(userDetails, storeRequestDto, storeId);
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<String> deleteStore(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long storeId){
        return storeService.deleteStore(userDetails, storeId);
    }

}
