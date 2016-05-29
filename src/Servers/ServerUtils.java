package servers;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import Corba.CORBA_Falae;
import Corba.CORBA_FalaeHelper;

public class ServerUtils {

	private static final String RMI_API = "RMIServerAPI";
	private static final String RMI_SERVER = "helton-pc";

	private static final String CORBA_SERVER = "helton-pc";

	public static RMIServerAPI getServerStub() throws RemoteException,
			NotBoundException {
		Registry registry = LocateRegistry.getRegistry(RMI_SERVER);
		System.out.println(registry);
		return (RMIServerAPI) registry.lookup(RMI_API);
	}

	public static CORBA_Falae getServerCorba() throws InvalidName,
			NotFound, CannotProceed,
			org.omg.CosNaming.NamingContextPackage.InvalidName {
		// Cria e inicializa o ORB
		String[] args = { "-ORBInitialPort", "2000", "-ORBInitialHost",
				CORBA_SERVER };

		ORB orb = ORB.init(args, null);

		// Obtem referencia para o servico de nomes
		org.omg.CORBA.Object objRef = orb
				.resolve_initial_references("NameService");
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

		// Obtem referencia para o servidor
		String name = "CORBA_Falae";
		CORBA_Falae server = CORBA_FalaeHelper.narrow(ncRef.resolve_str(name));

		return server;
	}

}
