package com.sparta.outsorcingproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.sparta.outsorcingproject.dto.ProfileModifyRequestDto;
import com.sparta.outsorcingproject.dto.ProfileRequestDto;
import com.sparta.outsorcingproject.dto.ProfileResponseDto;
import com.sparta.outsorcingproject.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/profile/get")
    public ResponseEntity<ProfileResponseDto> getProfile(@RequestParam String username) {
        ProfileRequestDto requestDto = new ProfileRequestDto(username);
        return profileService.showProfile(requestDto);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<ProfileResponseDto> updateProfile(@RequestBody @Valid ProfileModifyRequestDto modifyRequestDto) {
        return profileService.updateProfile(modifyRequestDto);
    }
}