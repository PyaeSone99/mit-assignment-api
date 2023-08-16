package com.example.demo.model.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer>{
	
	@Query("SELECT o FROM Order o JOIN FETCH o.orderItems oi JOIN FETCH oi.product p WHERE o.id = :orderId")
	Order findByIdWithDetails(@Param("orderId") int orderId);

	@Query("SELECT o FROM Order o WHERE " +
		       "o.customerName LIKE %:searchKey% " +
		       "OR o.customerPhoneNumber LIKE %:searchKey% " +
		       "OR o.address LIKE %:searchKey% " +
		       "OR o.orderId = :numericValue " + 
		       "OR (:numericValue IS NOT NULL AND o.orderId <= :numericValue)")
	Page<Order>findByCustomerNameOrCustomerPhoneNumberOrAddressOrOrderIdContaining(@Param("searchKey") String searchKey, @Param("numericValue") Integer numericValue,PageRequest pageRequest);
}

