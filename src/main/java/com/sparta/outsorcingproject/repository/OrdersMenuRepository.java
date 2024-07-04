package com.sparta.outsorcingproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsorcingproject.entity.OrdersMenu;

import java.util.List;

public interface OrdersMenuRepository extends JpaRepository<OrdersMenu, Long> {
    List<OrdersMenu> findAllByMenuId(long menuId);
}
