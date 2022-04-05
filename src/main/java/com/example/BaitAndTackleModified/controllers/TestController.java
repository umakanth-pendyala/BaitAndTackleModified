package com.example.BaitAndTackleModified.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.example.BaitAndTackleModified.entities.CartEntity;
import com.example.BaitAndTackleModified.entities.ProductEntity;
import com.example.BaitAndTackleModified.entities.UserEntity;
import com.example.BaitAndTackleModified.repositorys.CartRepository;
import com.example.BaitAndTackleModified.repositorys.ProductRepository;
import com.example.BaitAndTackleModified.repositorys.UserRepository;
import com.example.BaitAndTackleModified.utils.EmailServiceUtil;
import com.example.BaitAndTackleModified.utils.JwtTokenUtils;
import com.example.BaitAndTackleModified.utils.OtpUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TestController {
	
//	@Autowired EmailServiceUtil emailService;
//	JwtTokenUtils tokenObject = new JwtTokenUtils();
	
	@Autowired UserRepository userRepository;
	@Autowired ProductRepository productRepository;
	@Autowired CartRepository cartRepository;
	
	@GetMapping("/test")
	public Object testRoute() {
		Optional<UserEntity> user = userRepository.findById((long) 9);
		Optional<ProductEntity> product = productRepository.findById((long) 18);
		CartEntity cartObject = new CartEntity(user.get(), product.get(), 5, 10000);
		cartRepository.save(cartObject);
		return false;
	}
		
}


//HashMap<String, Object> claims = (HashMap<String, Object>) tokenObject.getClaimsFromToken(token);