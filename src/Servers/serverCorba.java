package servers;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import Corba.CORBA_Falae;
import Corba.CORBA_FalaeHelper;
import Implements.CORBA_FalaeImpl;

public class ServerCorba {

	public static void main(String args[]) {
		try {
			// Cria e inicializa o ORB
			ORB orb = ORB.init(args, null);

			// Cria a implementação e registra no ORB
			CORBA_FalaeImpl impl = new CORBA_FalaeImpl();

			// Ativa o POA
			POA rootpoa = POAHelper.narrow(orb
					.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();

			// Pega a referência do servidor
			org.omg.CORBA.Object ref = rootpoa.servant_to_reference(impl);
			CORBA_Falae href = CORBA_FalaeHelper.narrow(ref);

			// Obtém uma referência para o servidor de nomes
			org.omg.CORBA.Object objRef = orb
					.resolve_initial_references("NameService");
			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// Registra o servidor no servico de nomes
			String name = "CORBA_Falae";
			NameComponent path[] = ncRef.to_name(name);
			ncRef.rebind(path, href);

			System.out.println("Servidor aguardando requisicoes ....");

			// Aguarda chamadas dos clientes
			orb.run();
		} catch (Exception e) {
			System.err.println("ERRO: " + e);
			e.printStackTrace(System.out);
		}
		System.out.println("Encerrando o Servidor.");
	}

	public static CORBA_Falae getServerConnection(String serverName) {

		// Cria e inicializa o ORB
		String[] args = { "-ORBInitialPort", "2000", "-ORBInitialHost",
				serverName };

		ORB orb = ORB.init(args, null);

		org.omg.CORBA.Object objRef;
		try {
			// Obtem referencia para o servico de nomes
			objRef = orb.resolve_initial_references("NameService");

			NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

			// Obtem referencia para o servidor
			String name = "CORBA_Falae";
			CORBA_Falae server = CORBA_FalaeHelper.narrow(ncRef
					.resolve_str(name));

			return server;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
