package com.example.demo.model.services;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.AddOrderForm;
import com.example.demo.model.dto.OrderDto;
import com.example.demo.model.dto.OrderItemDetailsDto;
import com.example.demo.model.dto.OrderItemDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Order;
import com.example.demo.model.entity.OrderItem;
import com.example.demo.model.entity.Product;
import com.example.demo.model.repo.OrderItemRepo;
import com.example.demo.model.repo.OrderRepo;
import com.example.demo.model.repo.ProductRepo;

@Service
public class OrderServices {

	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private OrderItemRepo orderItemRepo;
	
	public Order create(AddOrderForm orderRequest) {
		int min = 1;
		int max = 1000;
		Random random = new Random();
		Order order = new Order();
		order.setOrderId(random.nextInt(max - min + 1)+ min);
		order.setCustomerName(orderRequest.getCustomerName());
		order.setCustomerPhoneNumber(orderRequest.getCustomerPhoneNumber());
		order.setAddress(orderRequest.getAddress());
		
		double totalOrderPrice = 0.0;
		for (OrderItemDto itemRequest : orderRequest.getOrderItems()) {
			Product product = productRepo.findById(itemRequest.getProductId()).orElse(null);
			System.out.println(product.getName());
			if (product != null) {
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(product);
			orderItem.setQuantity(itemRequest.getQuantity());
			order.getOrderItems().add(orderItem);
			totalOrderPrice += product.getPrice() * itemRequest.getQuantity();
			}
		}
		order.setTotalPrice(totalOrderPrice);
		return orderRepo.save(order);
	}

//	This is find All
	public Page<OrderDto> findAll(Optional<String>searchKey,
			int current,int size){ 
		Integer numericValue = null;
        if (searchKey.isPresent()) {
            String searchStr = searchKey.get();
            try {
                numericValue = Integer.parseInt(searchStr);
            } catch (NumberFormatException ignored) {
            }
        }
		Page<Order>orders =  orderRepo.findByCustomerNameOrCustomerPhoneNumberOrAddressOrOrderIdContaining(searchKey.orElse("").toLowerCase(),numericValue,PageRequest.of(current, size));
		List<OrderDto> allOrdersDto = orders.getContent().stream().map(ele -> OrderDto.orderData(ele)).toList();
		return new PageImpl<>(allOrdersDto, orders.getPageable(), orders.getTotalElements());
	}
	
	private static OrderDto mapOrderToOrderDto(Order order) {
		List<OrderItemDetailsDto> orderItemDetailsList = new ArrayList<OrderItemDetailsDto>();
		for(OrderItem orderItem : order.getOrderItems()) {
			orderItemDetailsList.add(new OrderItemDetailsDto(
					orderItem.getProduct().getName(),
					orderItem.getQuantity(),
					orderItem.getProduct().getPrice()
					));
		}
		
		return new OrderDto(
				order.getId(),
				order.getOrderId(),
				order.getCustomerName(),
				order.getCustomerPhoneNumber(),
				order.getAddress(),
				order.getTotalPrice(),
				orderItemDetailsList
				);
	}

//	This is find by id
	public OrderDto findByOrderId(int orderId) {
		Order order = orderRepo.findByIdWithDetails(orderId);
		return mapOrderToOrderDto(order);
	}

	
//	This is updating
	public Order updateOrder(int orderId, AddOrderForm orderRequest) {
		Optional<Order> order = orderRepo.findById(orderId);
		if (order.isPresent()) {
			Order existingOrder = order.get();
			existingOrder.setCustomerName(orderRequest.getCustomerName());
			existingOrder.setCustomerPhoneNumber(orderRequest.getCustomerPhoneNumber());
			existingOrder.setAddress(orderRequest.getAddress());
			
			 List<OrderItem> existingOrderItems = existingOrder.getOrderItems();
		      existingOrderItems.clear();

			for(OrderItemDto itemRequest : orderRequest.getOrderItems()) {
				Product product = productRepo.findById(itemRequest.getProductId()).orElse(null);
				if(product != null) {
					OrderItem existingOrderItem = findExistingOrderItem(existingOrderItems, itemRequest.getProductId());
					
					if (existingOrderItem != null) {
	                    existingOrderItem.setQuantity(itemRequest.getQuantity());
	                } else {
	                    OrderItem orderItem = new OrderItem();
	                    orderItem.setProduct(product);
	                    orderItem.setQuantity(itemRequest.getQuantity());
	                    existingOrderItems.add(orderItem);
	                }
				}
			}
			double totalOrderPrice = calculateTotalOrderPrice(existingOrder);
			existingOrder.setTotalPrice(totalOrderPrice);
			return orderRepo.save(existingOrder);
		}else {
			return null;
		}
	}

	
	private OrderItem findExistingOrderItem(List<OrderItem> existingOrderItems, int productId) {
	    for (OrderItem orderItem : existingOrderItems) {
	        if (orderItem.getProduct().getId()==(productId)) {
	            return orderItem;
	        }
	    }
	    return null;
	}
	
	private static double calculateTotalOrderPrice(Order order) {
		double totalOrderPrice = 0.0;
		for(OrderItem orderItem : order.getOrderItems()) {
			double productPrice = orderItem.getProduct().getPrice();
			int quantity = orderItem.getQuantity();
			totalOrderPrice += productPrice * quantity;
		}
		
		return totalOrderPrice;
	}
	
	
//delete order
	public void deleteOrder(int orderId) {
		Optional<Order> optionalOrder = orderRepo.findById(orderId);
		if(optionalOrder.isPresent()) {
			Order order = optionalOrder.get();
			List<OrderItem> existingOrderItems = order.getOrderItems();
		      existingOrderItems.clear();
			orderRepo.delete(order);
		}
		 
	}
}





































