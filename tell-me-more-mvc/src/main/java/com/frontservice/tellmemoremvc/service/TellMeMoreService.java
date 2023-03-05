package com.frontservice.tellmemoremvc.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.frontservice.tellmemoremvc.bean.Artist;

@Service
public class TellMeMoreService {

	public List<Artist> getTopArtists(){
		String uri = "http://localhost:8500/api/get-top-artists";
		RestTemplate rest = new RestTemplate();
		Artist[] artists = rest.getForObject(uri, Artist[].class);
		return Arrays.asList(artists);
	}
}
