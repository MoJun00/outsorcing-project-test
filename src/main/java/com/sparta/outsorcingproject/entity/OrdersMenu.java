package com.sparta.outsorcingproject.entity;

import com.sparta.outsorcingproject.dto.OrdersMenuRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrdersMenu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders_id")
	private Orders orders;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	private Menu menu;

	private long quantity;

	public OrdersMenu(OrdersMenuRequestDto ordersMenuDto, Orders orders, Menu menu) {
		this.menu = menu;
		this.quantity = ordersMenuDto.getQuantity();
		this.orders = orders;
	}

	public void addOrders(Orders orders) {
		this.orders = orders;
	}
}
