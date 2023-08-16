package com.example.demo.model.dto;

import org.springframework.web.multipart.MultipartFile;


public record AddProductForm(
		String code,
		String name,
		String description,
		MultipartFile file,
		double price,
		int quantity
		) {
	
}
