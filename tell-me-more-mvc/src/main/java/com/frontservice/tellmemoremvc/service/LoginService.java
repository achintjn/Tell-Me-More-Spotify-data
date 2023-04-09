package com.frontservice.tellmemoremvc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.frontservice.tellmemoremvc.bean.LoginUser;
import com.frontservice.tellmemoremvc.jpa.LoginUserDataRepository;

@Service
public class LoginService {
	
	@Autowired
	LoginUserDataRepository repo;
	
	@Autowired
	private PasswordEncoder encoder;

	public void addUser(LoginUser user) {
		// TODO Auto-generated method stub
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
		
	}
	
	public Optional<LoginUser> getAllUser(String name) {
		// TODO Auto-generated method stub
		
		return repo.findByName(name);
		
	}

}
