package Clients;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import Group.CRUD_Falae;
import Group.CRUD_FalaeHelper;

public class clientCorba {

	public static void main(String args[]) {

		try {
			CRUD_Falae server = getServerConnection("localhost");

			// Imprime mensagem de boas-vindas
			System.out.println("response: "
					+ server.createGroup(1, "nome", "desc", 10));
			System.out.println("response: "
					+ server.createGroup(2, "nome2", "desc2", 102));
			System.out.println("response: "
					+ server.createGroup(3, "nome3", "desc3", 11));

			System.out.println("\n LISTAR GRUPOS: \n" + server.getGroups());

			System.out.println("\n response: " + server.removeGroup(2));

			System.out.println("\n LISTAR GRUPOS: \n" + server.getGroups());

		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}

	private static CRUD_Falae getServerConnection(String serverName)
			throws InvalidName, NotFound, CannotProceed,
			org.omg.CosNaming.NamingContextPackage.InvalidName {
		// Cria e inicializa o ORB
		String[] args = { "-ORBInitialPort", "2000", "-ORBInitialHost",
				serverName };

		ORB orb = ORB.init(args, null);

		// Obtem referencia para o servico de nomes
		org.omg.CORBA.Object objRef = orb
				.resolve_initial_references("NameService");
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

		// Obtem referencia para o servidor
		String name = "CRUD_Falae";
		CRUD_Falae server = CRUD_FalaeHelper.narrow(ncRef.resolve_str(name));

		return server;
	}

}
