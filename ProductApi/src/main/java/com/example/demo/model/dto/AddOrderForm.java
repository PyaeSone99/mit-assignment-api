package com.example.demo.model.dto;

import java.util.List;

public class AddOrderForm {
	
	
	private String customerName;
	private String customerPhoneNumber;
	private String address;
	private List<OrderItemDto> orderItems;
	
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}
	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}
	
	
}
