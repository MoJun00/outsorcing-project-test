package com.sparta.outsorcingproject.dto;

import com.sparta.outsorcingproject.entity.OrdersMenu;

import lombok.Getter;

@Getter
public class OrdersMenuResponseDto {

	private long ordersMenuId;
	private long orders_id;
	private long menuId;
	private long quantity;

	public OrdersMenuResponseDto(OrdersMenu ordersMenu) {
		this.ordersMenuId = ordersMenu.getId();
		this.orders_id = ordersMenu.getOrders().getId();
		this.menuId = ordersMenu.getMenu().getId();
		this.quantity = ordersMenu.getQuantity();
	}
}
