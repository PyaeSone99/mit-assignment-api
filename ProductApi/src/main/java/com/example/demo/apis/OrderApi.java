package com.example.demo.apis;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.AddOrderForm;
import com.example.demo.model.dto.OrderDto;
import com.example.demo.model.entity.Order;
import com.example.demo.model.services.OrderServices;

@RestController
@RequestMapping("order")
public class OrderApi {
	
	@Autowired
	private OrderServices orderServices;

	@PostMapping("create")
	public Order create(@RequestBody AddOrderForm orderRequest) {
		return orderServices.create(orderRequest);
	}
	
	@GetMapping()
	public Page<OrderDto> getAllOrders(@RequestParam("searchKey")Optional<String> searchKey,
				@RequestParam(required = false,defaultValue = "0")int current,
				@RequestParam(required = false,defaultValue = "1")int size){
		return orderServices.findAll(searchKey,current,size);
	}
	
	@GetMapping("/details/{orderId}")
	public OrderDto getOrderDetails(@PathVariable int orderId) {
		return orderServices.findByOrderId(orderId);
	}
	
	@PutMapping("updateOrder/{orderId}")
	public Order updateOrder(@PathVariable int orderId,@RequestBody AddOrderForm orderRequest) {
		return orderServices.updateOrder(orderId,orderRequest);
	}
	
	@DeleteMapping("deleteOrder/{orderId}")
	public void deleteOrder(@PathVariable int orderId) {
		orderServices.deleteOrder(orderId);
	}
}







































