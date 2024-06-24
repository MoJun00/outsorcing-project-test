package com.sparta.outsorcingproject.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Follower {

	@EmbeddedId
	private FollowerId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("followerId")
	@JoinColumn(name = "follower_id")
	private User follower;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("meId")
	@JoinColumn(name = "me_id")
	private User me;

	public Follower(User follower, User me) {
		this.id = new FollowerId(follower.getId(), me.getId());
		this.follower = follower;
		this.me = me;
	}
}

