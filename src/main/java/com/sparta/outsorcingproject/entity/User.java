package com.sparta.outsorcingproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	private Long kakaoId;

    @Column
    private String profileUrl;

	public User(String username, String password, String introduce, UserRoleEnum role) {
		this.username = username;
		this.password = password;
		this.introduce = introduce;
		this.role = role;
	}

	public User(String username, String encodedPassword, UserRoleEnum userRoleEnum, Long kakaoId) {
		this.username = username;
		this.password = encodedPassword;
		this.role = userRoleEnum;
		this.kakaoId = kakaoId;
	}

	public void update(String username, String password, String introduce) {
		this.username = username;
		this.password = password;
		this.introduce = introduce;
	}

	public User kakaoIdUpdate(Long kakaoId) {
		this.kakaoId = kakaoId;
		return this;
	}
    public void updateProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

}
