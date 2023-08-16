package com.example.demo.model.dto;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

public record UpdateProductForm(
		String code,
		String name,
		String description,
		Optional<MultipartFile> file,
		double price,
		int quantity
		) {

}
