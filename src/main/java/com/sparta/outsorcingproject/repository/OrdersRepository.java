package com.sparta.outsorcingproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsorcingproject.entity.Orders;
import com.sparta.outsorcingproject.entity.User;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
	Page<Orders> findAllByOrderByCreatedAtDesc(PageRequest pageRequest);

	List<Orders> findAllByUser(User follower);
}
