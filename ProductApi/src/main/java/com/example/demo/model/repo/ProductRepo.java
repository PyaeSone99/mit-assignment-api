package com.example.demo.model.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Product;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;


public interface ProductRepo extends JpaRepository<Product, Integer>{

	 @Query("SELECT e FROM Product e " +
	           "WHERE e.code LIKE %:searchKey% " +
	           "OR e.name LIKE %:searchKey% " +
	           "OR e.price = :numericValue " +
	           "OR e.price <= :numericValue")
	    Page<Product> findByCodeOrNameOrPriceContaining(@Param("searchKey") String searchKey, @Param("numericValue") Double numericValue,PageRequest pageRequest);
	
	 @Query("SELECT p FROM Product p WHERE p.name = :name")
	 Product findByName(@Param("name")String name);
}
