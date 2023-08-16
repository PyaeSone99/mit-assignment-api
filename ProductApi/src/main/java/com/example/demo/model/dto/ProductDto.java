package com.example.demo.model.dto;

import com.example.demo.model.entity.Product;

public class ProductDto {
	
	private int id;
	private String name;
	private String code;
	private String description;
	private String imageName;
	private String filePath;
	private double price;
	private int quantity;
	
	
	public ProductDto() {
		super();
	}

	public ProductDto(int id, String name, String code, String description, String imageName,String filePath, double price,
			int quantity) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.description = description;
		this.imageName = imageName;
		this.filePath = filePath;
		this.price = price;
		this.quantity = quantity;
	}

	public static ProductDto productData(Product product) {
		return new ProductDto(product.getId(),product.getName(),product.getCode(),product.getDescription(),product.getImageName()
				,product.getFilePath(),product.getPrice(),product.getQuantity());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
}
