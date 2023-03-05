package com.micro.spotifyapp.spotifywrapsimulate.component;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.micro.spotifyapp.spotifywrapsimulate.configuration.Configuration;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;

@Component
public class SpotifyApiComponent {
	
	@Autowired
	private Configuration config;
	
	
	private String clientId; 
	private String clientSecret; //

	private  URI redirectUri; //SpotifyHttpManager.makeUri("http://localhost:8080/api/get-user-code");
	private  String code;
	
	private  SpotifyApi spotifyApi; 


	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public URI getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(URI redirectUri) {
		this.redirectUri = redirectUri;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public SpotifyApi getSpotifyApi() {
		return spotifyApi;
	}

	public void setSpotifyApi(SpotifyApi spotifyApi) {
		this.spotifyApi = spotifyApi;
	}
	
	public SpotifyApi initSpotifyApi() {
		
		setRedirectUri(SpotifyHttpManager.makeUri(config.getRedirectUri()));
		
		SpotifyApi build = new SpotifyApi.Builder()
	    .setClientId(config.getClientID())
	    .setClientSecret(config.getClientSecret())
	    .setRedirectUri(getRedirectUri())
	    .build();

		
		
		setSpotifyApi(build);
		
		return getSpotifyApi();
		
	}

}
