package com.micro.spotifyapp.spotifywrapsimulate.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.micro.spotifyapp.spotifywrapsimulate.bean.ArtistInfo;
import com.micro.spotifyapp.spotifywrapsimulate.component.SpotifyApiComponent;

import jakarta.servlet.http.HttpServletResponse;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopArtistsRequest;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest;

@RestController
@RequestMapping("/api")
public class SpotifyAuthController {
	
	@Autowired
	private SpotifyApiComponent spotifyComp;
	
	private static SpotifyApi spotifyApi;
	private static String code = "";

	@GetMapping("/login")
	public String spotifyLogin(HttpServletResponse response) throws IOException {
		spotifyApi = spotifyComp.initSpotifyApi();
		AuthorizationCodeUriRequest build = spotifyApi.authorizationCodeUri()
				.scope("user-read-private, user-read-email, user-top-read")
				.show_dialog(true)
				.build();
		final URI uri = build.execute();
		response.sendRedirect(uri.toString());
		return uri.toString();
	}
	
	@GetMapping("/get-user-code")
	public String getCodeSpotify(@RequestParam("code") String userCode, HttpServletResponse response, HttpServletResponse request) throws IOException {
		spotifyComp.setCode(userCode);
		code = spotifyComp.getCode();
		System.out.println("Code= "+code);
		
		final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
				    .build();
		
		try {
		      final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();

		      // Set access and refresh token for further "spotifyApi" object usage
		      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
		      spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());

		      System.out.println("Expires in: " + authorizationCodeCredentials.getExpiresIn());
		    } catch (IOException | SpotifyWebApiException e) {
		      System.out.println("Error: " + e.getMessage());
		    } catch (org.apache.hc.core5.http.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println("This is the header: "+response.getHeader("send_to"));
		//System.out.println(send_to);
		if(request.getHeader("send_to")!=null)
			response.sendRedirect(request.getHeader("send_to"));
		else 
			response.sendRedirect("http://localhost:8500/api/get-top-artists");
		
		return spotifyApi.getAccessToken();
	}
	
	@GetMapping("/get-top-artists")
	public List<ArtistInfo> getTopArtists(HttpServletResponse response) throws IOException {
		if(spotifyApi==null || spotifyApi.getAccessToken()==null) {
			response.setHeader("send_to", "/get-top-artists");
			response.sendRedirect("/api/login?send_to=/api/get-top-artists");
			return null;
		}
		
		GetUsersTopArtistsRequest getTopArtistReq = spotifyApi.getUsersTopArtists().time_range("medium_term").limit(10).offset(5).build();
		
		
		try {
			Artist[] items = getTopArtistReq.execute().getItems();
			List<ArtistInfo> list = new ArrayList<>();
			for(Artist item:items) {
				List<String> genres = new ArrayList<>();
				for(String genre:item.getGenres())
					genres.add(genre);
				list.add(new ArtistInfo(item.getName(), item.getImages()[1].getUrl(), item.getUri(), genres));
			}
			
			return list;
			
			
			
		} catch (org.apache.hc.core5.http.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SpotifyWebApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	@GetMapping("/get-top-track")
	public Track[] getTopTrack() {
		GetUsersTopTracksRequest getTopTrackReq = spotifyApi.getUsersTopTracks().build();
		
		try {
			return getTopTrackReq.execute().getItems();
		} catch (org.apache.hc.core5.http.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SpotifyWebApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
		
}
