package com.example.BaitAndTackleModified.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.BaitAndTackleModified.entities.CartEntity;
import com.example.BaitAndTackleModified.repositorys.CartRepository;

@Component
public class CartUtils {
	@Autowired CartRepository cartRepo;
	
	
	public Boolean checkIfItemExistsInCart(Long userId, Long productId) {
		List<CartEntity> cartItems = cartRepo.getIfTheProductExistsInUsersCart(userId, productId);
		if (cartItems.size() > 0) return true;
		return false;
	}
}
