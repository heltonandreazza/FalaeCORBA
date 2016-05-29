package token;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;

import json.Mapper;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import servers.RMIServerAPI;
import servers.ServerUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class TokenGenerator {

	private static final String HTTP_ISSUER = "http://helton-pc:8080";
	private static byte[] sharedSecret = new byte[32];

	// OBS: SE NÃO FUNCIONAR VERIFICAR O SHAREDSECRET

	// Teste
	public static void main(String[] args) throws JOSEException,
			ParseException, IOException {
		// sharedSecret = Files.readAllBytes(Paths.get("C:\\baseJson\\byte"));
		// gera token
		String s = generateToken("helton", "123");
		String a = generateToken("ana", "321");
		// exibe true se token ok exibe false se não ok

		System.out.println("helton " + verifyToken(s));
		System.out.println("ana " + verifyToken(a));

		// System.out.println(new Date(new Date().getTime() + 1800000));//30 min
		// System.out.println(new Date(new Date().getTime() + 60000));//1 min
		/*
		 * KeyGenerator kgen; try { kgen = KeyGenerator.getInstance("AES");
		 * kgen.init(256); SecretKey key = kgen.generateKey(); byte[] encoded =
		 * key.getEncoded(); Files.write(Paths.get("C:\\baseJson\\byte"),
		 * encoded); } catch (NoSuchAlgorithmException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
	}

	// User and UserPassword devem estar validados
	public static String generateToken(String userName, String userPassword) {

		if (!authenticUser(userName, userPassword)) {
			return "usuário e senha inválido";
		}

		// Generate random 256-bit (32-byte) shared secret
		SecureRandom random = new SecureRandom();
		random.nextBytes(sharedSecret);

		// Prepare JWT with claims set
		JWTClaimsSet claimSet = getClainSet(userName, userPassword);

		// Create SignedJWT and Apply the HMAC protection
		SignedJWT signedJWT = getSignedJWT(claimSet);

		String token = signedJWT.serialize();

		saveToken(userName, userPassword, token, signedJWT);
		saveLog(userName, token);
		
		
		// Serialize to compact form, produces something like
		// eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
		return token;
	}

	private static void saveLog(String userName, String userPassword) {
		try {
			RMIServerAPI rmi = ServerUtils.getServerStub();
			
			rmi.postLog(userName, userPassword, new Date(new Date().getTime()).toString());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static boolean authenticUser(String userName, String userPassword) {
		try {
			RMIServerAPI serverStub = ServerUtils.getServerStub();

			String usersJson = serverStub.getUsers();

			JSONObject user = new JSONObject(usersJson).getJSONObject(userName);

			String name = user.getString("name");
			String pass = user.getString("password");

			// retorna se é válido
			return StringUtils.equals(name, userName)
					&& StringUtils.equals(pass, userPassword);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			return false;
		}
		return false;
	}

	public static boolean verifyToken(String strToken) {
		Token readToken = Tokens.getToken(strToken);

		if (readToken == null) {
			return false;
		}

		// get assinatura
		SignedJWT signedJWT = readToken.getSignedJWT();

		signedJWT = paserToken(readToken.getToken());

		JWSVerifier verifier = getMACVerifier();

		// Retrieve / verify the JWT claims according to the app requirements
		try {
			// verify signedJWT
			boolean signedJWTIsVerify = signedJWT.verify(verifier);

			// verify claimsSet
			// verify subject
			String subject = signedJWT.getJWTClaimsSet().getSubject();
			boolean subjectIsEquals = StringUtils.equals(
					readToken.getUserPassword() + readToken.getUserName(),
					signedJWT.getJWTClaimsSet().getSubject());
			System.out.println(subject + ":" + subjectIsEquals);
			// verify issuer
			String issuer = signedJWT.getJWTClaimsSet().getIssuer();
			boolean issuerIsEquals = StringUtils.equals(HTTP_ISSUER, signedJWT
					.getJWTClaimsSet().getIssuer());
			System.out.println(issuer + ":" + issuerIsEquals);
			// verify expiration time
			Date expirationTime = signedJWT.getJWTClaimsSet()
					.getExpirationTime();
			boolean todayIsbeforeExpirantion = new Date().before(signedJWT
					.getJWTClaimsSet().getExpirationTime());
			System.out.println(expirationTime + ":" + todayIsbeforeExpirantion);

			// final verify response (all need to be true)
			return subjectIsEquals && issuerIsEquals
					&& todayIsbeforeExpirantion;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private static <T> T readJson(String json, Class<T> type) {
		try {
			return Mapper.getInstance().readValue(json, type);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void saveToken(String userName, String userPassword,
			String token, SignedJWT signedJWT) {
		Tokens.postToken(new Token(token, userName, userPassword, signedJWT));
	}

	private static JWTClaimsSet getClainSet(String userName, String userPassword) {
		Date date = new Date(new Date().getTime() + 1800000);// 30 min
		// Prepare JWT with claims set
		return new JWTClaimsSet.Builder().subject(userPassword + userName)
				.expirationTime(date).issuer(HTTP_ISSUER).build();
	}

	private static SignedJWT getSignedJWT(JWTClaimsSet claimsSet) {
		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256),
				claimsSet);

		// Create HMAC signer
		JWSSigner signer = getSigner();

		// Apply the HMAC protection
		try {
			signedJWT.sign(signer);
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return signedJWT;
	}

	private static JWSSigner getSigner() {
		try {
			return new MACSigner(sharedSecret);
		} catch (KeyLengthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static JWSVerifier getMACVerifier() {
		try {
			return new MACVerifier(sharedSecret);
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static SignedJWT paserToken(String token) {
		try {
			return SignedJWT.parse(token);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
