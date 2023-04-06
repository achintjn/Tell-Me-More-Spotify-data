package com.frontservice.tellmemoremvc.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import com.frontservice.tellmemoremvc.bean.LoginUser;
import com.frontservice.tellmemoremvc.jpa.LoginUserDataRepository;


@Component
public class LoginInfoUserDetailsService implements UserDetailsService {
	
	@Autowired
	LoginUserDataRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		Optional<LoginUser> findByName = repo.findByName(username);
		System.out.println(findByName.toString());
		System.out.println("Inside A Method");
		
		return findByName.map(LoginUserInfoUserDetails::new)
				.orElseThrow(()->new UserNotFoundException("User Not Found"));
		
	}
	
	

}
