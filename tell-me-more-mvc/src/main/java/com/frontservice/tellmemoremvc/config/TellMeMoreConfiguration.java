package com.frontservice.tellmemoremvc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("tell-me-more-mvc")
public class TellMeMoreConfiguration {
	
	private String spotifyUrl;
	

	public String getSpotifyUrl() {
		return spotifyUrl;
	}

	public void setSpotifyUrl(String spotifyUrl) {
		this.spotifyUrl = spotifyUrl;
	}
	
	

}
