package token;

import java.util.HashMap;

public class Tokens {

	private static HashMap<String, Token> tokens = new HashMap<String, Token>();

	public static void postToken(Token Token) {
		tokens.put(Token.getToken(), Token);
	}

	public static void deleteToken(String token) {
		tokens.remove(token);
	}

	public static Token getToken(String token) {
		Token t = tokens.get(token);
		if( t == null) {
			return null;
		}
		return t;
	}
}
