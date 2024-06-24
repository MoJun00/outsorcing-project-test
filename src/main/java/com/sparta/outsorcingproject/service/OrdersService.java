package com.sparta.outsorcingproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.outsorcingproject.dto.OrdersMenuRequestDto;
import com.sparta.outsorcingproject.dto.OrdersRequestDto;
import com.sparta.outsorcingproject.dto.OrdersResponseDto;
import com.sparta.outsorcingproject.entity.Menu;
import com.sparta.outsorcingproject.entity.Orders;
import com.sparta.outsorcingproject.entity.OrdersMenu;
import com.sparta.outsorcingproject.entity.Store;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.repository.FollowRepository;
import com.sparta.outsorcingproject.repository.MenuRepository;
import com.sparta.outsorcingproject.repository.OrdersMenuRepository;
import com.sparta.outsorcingproject.repository.OrdersRepository;
import com.sparta.outsorcingproject.repository.StoreRepository;
import com.sparta.outsorcingproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersService {

	private final StoreRepository storeRepository;
	private final MessageSource messageSource;
	private final OrdersRepository ordersRepository;
	private final OrdersMenuRepository ordersMenuRepository;
	private final MenuRepository menuRepository;
	private final FollowRepository followRepository;
	private final UserRepository userRepository;

	@Transactional
	public OrdersResponseDto createOrders(User user, long storeId, OrdersRequestDto requestDto) {

		Store store = storeRepository.findById(storeId).orElseThrow(
			() -> new IllegalArgumentException(
				messageSource.getMessage("not.find.store", null, Locale.getDefault())
			)
		);

		Orders orders = new Orders(user, store);
		Orders savedOrders = ordersRepository.save(orders);

		ordersMenuConvert(requestDto, savedOrders);

		return new OrdersResponseDto(savedOrders);
	}

	@Transactional
	public OrdersResponseDto editOrders(User user, long ordersId, OrdersRequestDto requestDto) {
		Orders orders = ordersRepository.findById(ordersId).orElseThrow(
			() -> new IllegalArgumentException(
				messageSource.getMessage("not.find.orders", null, Locale.getDefault())
			)
		);

		if (!orders.getUser().getId().equals(user.getId())) {
			throw new IllegalArgumentException(
				messageSource.getMessage("mismatch.user", null, Locale.getDefault())
			);
		}

		orders.getOrdersMenu().clear();

		ordersMenuConvert(requestDto, orders);

		return new OrdersResponseDto(orders);
	}

	public List<OrdersResponseDto> findAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);

		Page<Orders> orders = ordersRepository.findAllByOrderByCreatedAtDesc(pageRequest);
		List<OrdersResponseDto> responseDtoList = new ArrayList<>();

		for (Orders order : orders) {
			responseDtoList.add(new OrdersResponseDto(order));
		}

		return responseDtoList;
	}

	public OrdersResponseDto find(long ordersId) {

		Orders orders = ordersRepository.findById(ordersId).orElseThrow(
			() -> new IllegalArgumentException(
				messageSource.getMessage("not.find.orders", null, Locale.getDefault())
			)
		);

		return new OrdersResponseDto(orders);
	}

	public void delete(long ordersId, User user) {
		Orders orders = ordersRepository.findById(ordersId).orElseThrow(
			() -> new IllegalArgumentException(
				messageSource.getMessage("not.find.orders", null, Locale.getDefault())
			)
		);

		if (!orders.getUser().getId().equals(user.getId())) {
			throw new IllegalArgumentException(
				messageSource.getMessage("mismatch.user", null, Locale.getDefault())
			);
		}

		ordersRepository.deleteById(ordersId);
	}

	private void ordersMenuConvert(OrdersRequestDto requestDto, Orders savedOrders) {
		long totalPrice = 0;

		for (OrdersMenuRequestDto ordersMenuDto : requestDto.getOrdersMenuList()) {
			Menu menu = menuRepository.findById(ordersMenuDto.getMenuId()).orElseThrow(
				() -> new IllegalArgumentException(
					messageSource.getMessage("not.find.menu", null, Locale.getDefault())
				)
			);

			totalPrice += menu.getPrice() * ordersMenuDto.getQuantity();

			OrdersMenu ordersMenu = ordersMenuRepository.save(
				new OrdersMenu(ordersMenuDto, savedOrders, menu));
			savedOrders.setTotalPrice(totalPrice);
			savedOrders.addOrdersMenu(ordersMenu);
		}
	}

	public List<OrdersResponseDto> findAllByFollow(User user, long followerId) {
		User follower = userRepository.findById(followerId).orElseThrow(
			() -> new IllegalArgumentException(
				messageSource.getMessage("not.find.user", null, Locale.getDefault())
			)
		);

		followRepository.findByFollowerAndMe(follower, user).orElseThrow(
			() -> new IllegalArgumentException(
				messageSource.getMessage("not.find.follower", null, Locale.getDefault())
			)
		);

		List<OrdersResponseDto> responseDtoList = new ArrayList<>();

		List<Orders> followerOrdersList = ordersRepository.findAllByUserOrderByCreatedAtDesc(follower);

		for (Orders orders : followerOrdersList) {
			responseDtoList.add(new OrdersResponseDto(orders));
		}

		return responseDtoList;
	}
}
