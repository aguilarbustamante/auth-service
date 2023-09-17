package es.vive.libre.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {

	@JsonProperty("auth-vivelibre-token")
	private String token;
	private String date;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
