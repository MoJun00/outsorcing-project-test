package com.sparta.outsorcingproject.entity;

import com.sparta.outsorcingproject.dto.ProfileModifyRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Column
    private String introduce;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role;



    public User(String username, String password, String introduce, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.introduce = introduce;
        this.role = role;
    }

    public void update(String username,String password,String introduce) {
            this.username = username;
            this.password = password;
            this.introduce = introduce;
    }

}
