package com.example.BaitAndTackleModified.utils;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.BaitAndTackleModified.entities.CartEntity;
import com.example.BaitAndTackleModified.entities.OrderEntity;
import com.example.BaitAndTackleModified.entities.ProductEntity;
import com.example.BaitAndTackleModified.entities.UserEntity;
import com.example.BaitAndTackleModified.repositorys.CartRepository;
import com.example.BaitAndTackleModified.repositorys.OrderRepository;
import com.example.BaitAndTackleModified.repositorys.ProductRepository;
import com.example.BaitAndTackleModified.repositorys.UserRepository;

@Component
public class OrderUtils {
	@Autowired UserRepository userRepo;
	@Autowired ProductRepository productRepo;
	@Autowired CartRepository cartRepo;
	@Autowired OrderRepository orderRepo;
	
	public OrderUtils() {}
	
	public Object getOrderItems(Long userId) {
		try {
			return orderRepo.findByUserId(userId);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	public Object orderAllItems(Long userId) {
		try {
			Optional<UserEntity> user = userRepo.findById(Long.valueOf(userId));
			if (user.isEmpty()) return false;
			List<CartEntity> cartItems = cartRepo.getAllCartElementsOfAUser(userId);
			if (cartItems.size() > 0) {
				for (int i = 0; i < cartItems.size(); i++) {
					if (cartItems.get(i).getQuantity() > cartItems.get(i).getProduct().getStockQuantity()) return false;
				}
				for (int i = 0; i < cartItems.size(); i++) {
					Integer quantityOrdered = cartItems.get(i).getQuantity();
					Integer newStock = cartItems.get(i).getProduct().getStockQuantity() - quantityOrdered;
					Integer rowsUpdated;
					rowsUpdated = (Integer)cartRepo.deleteCartItemBasedOnUserIdAndCartId(user.get().getUser_id(), cartItems.get(i).getCart_id());
					rowsUpdated = productRepo.updateStockQuantity(cartItems.get(i).getProduct().getProduct_id(), newStock);
					OrderEntity newOrder = new OrderEntity(cartItems.get(i).getUser(), 
							cartItems.get(i).getProduct(), 
							cartItems.get(i).getQuantity(), 
							cartItems.get(i).getQuantity() * cartItems.get(i).getPrice(), 
							"order placed", 
							cartItems.get(i).getPrice());
					orderRepo.save(newOrder);
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

	}
}

/*
 * Optional<ProductEntity> product = productRepo.findById(Long.valueOf(3));
		Optional<CartEntity> cart = cartRepo.findById(Long.valueOf(20));
		if (user.isPresent() && product.isPresent() && cart.isPresent()) {
			if (cart.get().getQuantity() > product.get().getStockQuantity()) {
				
			} else {
				Integer quantityOrdered = cart.get().getQuantity();
				Integer newStock = product.get().getStockQuantity() - quantityOrdered;
				Integer rowsUpdated;
				rowsUpdated = (Integer)cartRepo.deleteCartItemBasedOnUserIdAndCartId(user.get().getUser_id(), cart.get().getCart_id());
				rowsUpdated = productRepo.updateStockQuantity(product.get().getProduct_id(), newStock);
				if (rowsUpdated == 1) {
						OrderEntity newOrder = new OrderEntity(user.get(), product.get(), quantityOrdered, quantityOrdered * product.get().getPrice() , "placed", product.get().getPrice());
						Object temp = orderRepo.save(newOrder);
						System.out.println(temp);
						return true;
				} else {
					return false;
				}
			}
		} else return false;
		
		return false;*/
