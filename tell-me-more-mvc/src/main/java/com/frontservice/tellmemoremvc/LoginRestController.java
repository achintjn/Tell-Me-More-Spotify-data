package com.frontservice.tellmemoremvc;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.frontservice.tellmemoremvc.bean.LoginUser;
import com.frontservice.tellmemoremvc.service.LoginService;

@RestController
public class LoginRestController {

	
	@Autowired
	private LoginService loginService;

	@PostMapping("/addUser")
	public String addLoginUsers(@RequestBody LoginUser user) {
		loginService.addUser(user);
		return "User Added";
		
	}
	
	@GetMapping("/allUsers/{name}")
	public Optional<LoginUser> getUserInfo(@PathVariable String name) {
		
		return loginService.getAllUser(name);
		
	}
}
