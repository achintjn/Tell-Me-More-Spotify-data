package com.micro.spotifyapp.spotifywrapsimulate.bean;

import java.util.List;

public class ArtistInfo {
	
	String a_name;
	String a_picture;
	String a_spotify_url;
	List<String> a_genre;
	
	
	
	public ArtistInfo(String a_name, String a_picture, String a_spotify_url, List<String> a_genre) {
		super();
		this.a_name = a_name;
		this.a_picture = a_picture;
		this.a_spotify_url = a_spotify_url;
		this.a_genre = a_genre;
	}
	public String getA_name() {
		return a_name;
	}
	public void setA_name(String a_name) {
		this.a_name = a_name;
	}
	public String getA_picture() {
		return a_picture;
	}
	public void setA_picture(String a_picture) {
		this.a_picture = a_picture;
	}
	public String getA_spotify_url() {
		return a_spotify_url;
	}
	public void setA_spotify_url(String a_spotify_url) {
		this.a_spotify_url = a_spotify_url;
	}
	public List<String> getA_genre() {
		return a_genre;
	}
	public void setA_genre(List<String> a_genre) {
		this.a_genre = a_genre;
	}
	
	

}
