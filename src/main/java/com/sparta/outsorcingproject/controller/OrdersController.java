package com.sparta.outsorcingproject.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsorcingproject.dto.OrdersRequestDto;
import com.sparta.outsorcingproject.dto.OrdersResponseDto;
import com.sparta.outsorcingproject.entity.User;
import com.sparta.outsorcingproject.service.OrdersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

	private final OrdersService ordersService;
	private final String DELETE = "삭제 완료";

	@PostMapping("/{storeId}")
	public ResponseEntity<OrdersResponseDto> createOrders(@AuthenticationPrincipal User user,
		@PathVariable long storeId, OrdersRequestDto requestDto) {

		OrdersResponseDto responseDto = ordersService.createOrders(user, storeId, requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}

	@PutMapping("/{orders_id}")
	public ResponseEntity<OrdersResponseDto> editOrders(@AuthenticationPrincipal User user,
		@PathVariable long orders_id, OrdersRequestDto requestDto) {

		OrdersResponseDto responseDto = ordersService.editOrders(user, orders_id, requestDto);
		return ResponseEntity.ok().body(responseDto);
	}

	@GetMapping
	public ResponseEntity<List<OrdersResponseDto>> getOrders(
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "5") int size
	) {

		List<OrdersResponseDto> responseDtoList = ordersService.findAll(page, size);

		return ResponseEntity.ok().body(responseDtoList);
	}

	@GetMapping("/{ordersId}")
	public ResponseEntity<OrdersResponseDto> getOrders(
		@PathVariable long ordersId) {

		OrdersResponseDto responseDto = ordersService.find(ordersId);

		return ResponseEntity.ok().body(responseDto);
	}

	@DeleteMapping("/{ordersId}")
	public ResponseEntity<String> deleteOrders(
		@PathVariable long ordersId,
		@AuthenticationPrincipal User user) {

		ordersService.delete(ordersId, user);

		return ResponseEntity.ok().body(ordersId + DELETE);
	}
}
