package com.sparta.outsorcingproject.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.outsorcingproject.dto.OrdersRequestDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Orders extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	@OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrdersMenu> ordersMenu = new ArrayList<>();

	private long totalPrice;

	public Orders(User user, Store store) {
		this.user = user;
		this.store = store;
	}

	public void addOrdersMenu(OrdersMenu ordersMenu) {
		this.ordersMenu.clear();
		this.ordersMenu.add(ordersMenu);
		ordersMenu.addOrders(this);
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
}
