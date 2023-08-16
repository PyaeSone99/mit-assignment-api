package com.example.demo.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entity.OrderItem;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Integer>{

//	@Query("""
//			Delete From OrderItem where OrderItem.order_id = :id
//			""")
//	void deleteByOrderId(int id);
}
