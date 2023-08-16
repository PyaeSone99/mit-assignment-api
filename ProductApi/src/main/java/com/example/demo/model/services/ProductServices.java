package com.example.demo.model.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.model.dto.AddProductForm;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.dto.UpdateProductForm;
import com.example.demo.model.entity.Product;
import com.example.demo.model.repo.ProductRepo;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class ProductServices {
	
	@Value("${app.file.path}")
	String filePath;
	

	@Autowired
	private ProductRepo productRepo;

	public ProductDto create(@Valid AddProductForm form) throws IOException{
		String mainFilePath = filePath + form.file().getOriginalFilename();
		Files.copy(form.file().getInputStream(), Paths.get(mainFilePath),StandardCopyOption.REPLACE_EXISTING);
		Product product = new Product();
		product.setCode(form.code());
		product.setName(form.name());
		product.setDescription(form.description());
		product.setImageName(form.file().getOriginalFilename());
		product.setFilePath(mainFilePath);
		product.setPrice(form.price());
		product.setQuantity(form.quantity());
		return ProductDto.productData(productRepo.save(product));
	}

	public Optional<Product> findByProductId(int id) {
		return productRepo.findById(id);
	}
	
	public void delete(int id) {
		productRepo.deleteById(id);
	}
	
	@Transactional
	public ProductDto update(@Valid UpdateProductForm form,int id) throws IOException{
		String mainFilePath = null;
		if (form.file().isPresent()) {
			mainFilePath = filePath + form.file().get().getOriginalFilename();
			Files.copy(form.file().get().getInputStream(), Paths.get(mainFilePath),StandardCopyOption.REPLACE_EXISTING);
		}
		
		final String filePathToUpdate = mainFilePath;
		return productRepo.findById(id).map(pro -> {
			pro.setCode(form.code());
			pro.setName(form.name());
			pro.setDescription(form.description());
			
			if (form.file().isPresent()) {
				pro.setImageName(form.file().get().getOriginalFilename());
				pro.setFilePath(filePathToUpdate);
			}
			pro.setPrice(form.price());
			pro.setQuantity(form.quantity());
			return ProductDto.productData(pro);
		}).orElseThrow(EntityNotFoundException::new);
	}
	
	

	public Page<List<ProductDto>> findAll(Optional<String>searchKey,
									int current,int size) {
		Double numericValue = null;
        if (searchKey.isPresent()) {
            String searchStr = searchKey.get();
            try {
                numericValue = Double.parseDouble(searchStr);
            } catch (NumberFormatException ignored) {
            }
        }
		Page<Product> productBySearchKey = productRepo.findByCodeOrNameOrPriceContaining(searchKey.orElse("").toLowerCase(), numericValue, PageRequest.of(current, size));
		List<ProductDto> allProductDtos = productBySearchKey.getContent().stream().map(ele -> ProductDto.productData(ele)).toList();
		return new PageImpl<>(List.of(allProductDtos),productBySearchKey.getPageable(),productBySearchKey.getTotalElements());

	}
	
	
	public ProductDto findbyProductName(String name) {
		return ProductDto.productData(productRepo.findByName(name));
	}
	
}







































