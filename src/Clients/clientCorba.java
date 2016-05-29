package clients;

import java.util.Date;

import servers.RMIServerAPI;
import servers.ServerUtils;
import Corba.CORBA_Falae;

public class ClientCorba {

	public static void main(String args[]) {

		try {
			CORBA_Falae server = ServerUtils.getServerCorba();
			
			testAllServices(server);

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}

	private static void testAllServices(CORBA_Falae server) {

		System.out.println();
		System.out.println("post log: " + server.postLog("helton", "asdsad", new Date(new Date().getTime()).toString()));
		System.out.println("post log: " + server.postLog("helton2", "684684", new Date(new Date().getTime()).toString()));

		System.out.println("\n LISTAR LOGS: \n" + server.getLogs());
		
		System.out.println();
		String generateToken = server.generateToken("helton", "123");		
		System.out.println("generate token: " + generateToken);
		
		System.out.println("verify a token: " + server.verifyToken(generateToken));
		
		System.out.println("\n LISTAR USERS: \n" + server.getUsers());
	}

}
