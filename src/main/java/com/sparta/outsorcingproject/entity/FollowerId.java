package com.sparta.outsorcingproject.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FollowerId implements Serializable {

	@Column(name = "follower_id")
	private Long followerId;

	@Column(name = "me_id")
	private Long meId;
	// 기본 생성자, getter, setter, equals, hashCode 메서드
}
