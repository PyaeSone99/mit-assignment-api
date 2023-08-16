package com.example.demo.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.model.entity.Product;

public class OrderDto { //This is for OrderDetails 
	private int id;
	private int orderId;
	private String customerName;
	private String customerPhone;
	private String address;
	private double totalPrice;
	private List<OrderItemDetailsDto> orderItems;
	
	public OrderDto() {
		super();
	}
	
	public OrderDto(int id,int orderId, String customerName, String customerPhone, String address, double totalPrice,
			List<OrderItemDetailsDto> orderItems) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.customerName = customerName;
		this.customerPhone = customerPhone;
		this.address = address;
		this.totalPrice = totalPrice;
		this.orderItems = orderItems;
	}
	
	public static OrderDto orderData(Order order) {
		return new OrderDto(order.getId(),order.getOrderId(),order.getCustomerName(),order.getCustomerPhoneNumber(),
				order.getAddress(),order.getTotalPrice(),orderItemlist(order.getOrderItems()));
	}
	
	private static List<OrderItemDetailsDto> orderItemlist(List<OrderItem> orderItem) {
		List<OrderItemDetailsDto> orderItemDetailsList = new ArrayList<OrderItemDetailsDto>();
		for(OrderItem orderItemData : orderItem) {
			orderItemDetailsList.add(new OrderItemDetailsDto(
					orderItemData.getProduct().getName(),
					orderItemData.getQuantity(),
					orderItemData.getProduct().getPrice()
					));
		}
		return orderItemDetailsList;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<OrderItemDetailsDto> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemDetailsDto> orderItems) {
		this.orderItems = orderItems;
	}
	
	
}
