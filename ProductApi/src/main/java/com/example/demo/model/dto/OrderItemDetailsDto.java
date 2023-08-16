package com.example.demo.model.dto;

public class OrderItemDetailsDto {
	
	private String productName;
	private int quantity;
	private double productPrice;
	
	
	
	public OrderItemDetailsDto() {
		super();
	}
	public OrderItemDetailsDto(String productName, int quantity, double productPrice) {
		super();
		this.productName = productName;
		this.quantity = quantity;
		this.productPrice = productPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	
}
