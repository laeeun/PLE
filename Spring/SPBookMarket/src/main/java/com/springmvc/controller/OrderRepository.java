package com.springmvc.controller;

import com.springmvc.domain.Order;

public interface OrderRepository {
	Long saveOrder(Order order);
}
