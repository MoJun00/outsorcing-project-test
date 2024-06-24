package com.sparta.outsorcingproject.service;

import com.sparta.outsorcingproject.dto.AdminSignupRequestDto;
import com.sparta.outsorcingproject.dto.SignupReponseDto;
import com.sparta.outsorcingproject.entity.UserRoleEnum;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import com.sparta.outsorcingproject.dto.LoginRequestDto;
import com.sparta.outsorcingproject.dto.SignupRequestDto;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.jwt.JwtUtil;
import com.sparta.outsorcingproject.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    //admin 회원가입
    public ResponseEntity<SignupReponseDto> adminSignup(AdminSignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String introduce = requestDto.getIntroduce();
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRoleEnum role = UserRoleEnum.USER;

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, introduce, role);
        userRepository.save(user);

        return ResponseEntity.ok(new SignupReponseDto(user));
    }





    //user 회원가입
    public ResponseEntity<SignupReponseDto> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String introduce = requestDto.getIntroduce();
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRoleEnum role = UserRoleEnum.USER;

        // 회원 중복 확인

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 등록
        User user = new User(username, password, introduce, role);
        userRepository.save(user);

        return ResponseEntity.ok(new SignupReponseDto(user));
    }


    //계정 삭제
    @Transactional
    public ResponseEntity<String> deleteById(Long id,String password) {
        User user = userRepository.findById(id).orElseThrow(
                EntityNotFoundException::new
        );

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.deleteById(id);
        return ResponseEntity.ok("회원탈퇴 완료");
    }




    public ResponseEntity<String> login(LoginRequestDto requestDto, HttpServletResponse res) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);

        return ResponseEntity.ok("로그인 완료");

    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));
    }
}
