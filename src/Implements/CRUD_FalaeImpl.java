package Implements;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rmi.GroupAPI;
import Group.CRUD_FalaePOA;

public class CRUD_FalaeImpl extends CRUD_FalaePOA {

	private GroupAPI stub;
	
	public CRUD_FalaeImpl() throws RemoteException, NotBoundException {
		super();
		this.stub = getStub("GroupAPI");
	}

	public String inserir_grupo() {
		return "grupo inserido com sucesso";
	};

	public String createGroup(int id, String name, String desc, int rating) {
		try {
			return stub.createGroup(id, name, desc, rating);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getGroups() {
		try {
			return stub.getGroups();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String removeGroup(int id) {
		try {
			return stub.removeGroup(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private GroupAPI getStub(String api) throws RemoteException,
			NotBoundException {
		Registry registry = LocateRegistry.getRegistry("localhost");
		return (GroupAPI) registry.lookup(api);
	}

}