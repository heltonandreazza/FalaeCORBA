package token;

import json.Mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.SignedJWT;

public class Token {

	private String token;
	private String userName;
	private String userPassword;
	private SignedJWT signedJWT;

	public Token() {};
	
	public Token(String token, String userName, String userPassword, SignedJWT signedJWT) {
		this.token = token;
		this.userName = userName;
		this.userPassword = userPassword;
		this.signedJWT = signedJWT;
	}

	
	public SignedJWT getSignedJWT() {
		return signedJWT;
	}

	public void setSignedJWT(SignedJWT signedJWT) {
		this.signedJWT = signedJWT;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String toString() {
		return toJson();
	}

	public String toJson() {
		ObjectMapper mapper = Mapper.getInstance();

		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
