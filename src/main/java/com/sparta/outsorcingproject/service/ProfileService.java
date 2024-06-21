package com.sparta.outsorcingproject.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.sparta.outsorcingproject.dto.ProfileModifyRequestDto;
import com.sparta.outsorcingproject.dto.ProfileRequestDto;
import com.sparta.outsorcingproject.dto.ProfileResponseDto;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ProfileResponseDto> showProfile(ProfileRequestDto requestDto) {
        String requestUsername = requestDto.getUsername();
        User requestUser = userRepository.findByUsername(requestUsername)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "잘못된 접근입니다."));

        return ResponseEntity.ok(new ProfileResponseDto(requestUser));
    }

    @Transactional
    public ResponseEntity<ProfileResponseDto> updateProfile(ProfileModifyRequestDto modifyRequestDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        nullCheck(modifyRequestDto);
        User authorizeUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "잘못된 접근입니다."));
        writePasswordCheck(modifyRequestDto, authorizeUser);

        authorizeUser.update(modifyRequestDto);
        return ResponseEntity.ok(new ProfileResponseDto(authorizeUser));
    }

    private void writePasswordCheck(ProfileModifyRequestDto modifyRequestDto, User user) {
        String newPW = modifyRequestDto.getNewPassword();
        String oldPW = modifyRequestDto.getOldPassword();
        String originalPW = user.getPassword();

        if (newPW != null && oldPW == null) {
            throw new IllegalArgumentException("현재 비밀번호를 입력하지 않으셨습니다.");
        }
        if (newPW == null && oldPW != null) {
            throw new IllegalArgumentException("새 비밀번호를 입력하지 않으셨습니다.");
        }
        if (newPW != null && oldPW != null) {
            passwordCheck(newPW, oldPW, originalPW);
        }
    }

    private void passwordCheck(String newPW, String oldPW, String originalPW) {
        if (!passwordEncoder.matches(oldPW, originalPW)) {
            throw new IllegalArgumentException("현재 비밀번호를 잘못 입력하셨습니다.");
        }
        if (newPW.equals(oldPW)) {
            throw new IllegalArgumentException("이미 사용중인 비밀번호 입니다.");
        }
    }

    private void nullCheck(ProfileModifyRequestDto dto) {
        boolean fieldIsNull = true;
        try {
            Field[] fields = dto.getClass().getDeclaredFields();
            for (Field field : fields) {
                log.warn(field.getName() + " : ");
                field.setAccessible(true);
                Object value = field.get(dto);
                if (value == null) {
                    log.warn("null입니다.");
                } else {
                    log.warn(value.toString());
                    fieldIsNull = false;
                }
            }
        } catch (Exception e) {
            log.warn("Null Check 중 error 발생");
        }
        if (fieldIsNull) {
            throw new NoResultException("수정할 내용이 없습니다.");
        }
    }
}