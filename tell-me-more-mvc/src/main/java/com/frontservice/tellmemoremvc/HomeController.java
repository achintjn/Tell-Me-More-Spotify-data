package com.frontservice.tellmemoremvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.frontservice.tellmemoremvc.bean.LoginUser;
import com.frontservice.tellmemoremvc.service.LoginService;
import com.frontservice.tellmemoremvc.service.TellMeMoreService;

@Controller
public class HomeController {
	
	@Autowired
	TellMeMoreService tmmService;
	
	
	
	@GetMapping({"/","/home"})
	public String home(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "home";
	}
	
	@GetMapping("/showMyLoginPage")
	public String customLogin() {
		
		return "login";
	}
	
	@GetMapping("/subscription")
	public String subscriptionPage() {
		
		return "subscription";
	}
	
	@GetMapping("/get-top-Artists")
	public String getSpotifyArtists(Model model) {
		model.addAttribute("artist",tmmService.getTopArtists());
		System.out.println("Model: "+model);
		return "topArtist";
	}
	
}
