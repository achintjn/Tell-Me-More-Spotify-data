package com.frontservice.tellmemoremvc.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import com.frontservice.tellmemoremvc.bean.LoginUser;
import com.frontservice.tellmemoremvc.jpa.LoginUserDataRepository;


@Component
public class LoginInfoUserDetailsService extends InMemoryUserDetailsManager {
	
	@Autowired
	LoginUserDataRepository repo;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Optional<LoginUser> findByName = repo.findByName(username);
		
		return findByName.map(LoginUserInfoUserDetails::new)
				.orElseThrow(()->new UserNotFoundException("User Not Found"));
		
	}
	
	

}
