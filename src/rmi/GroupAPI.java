package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GroupAPI extends Remote {
	String createGroup(int id, String name, String description, int rating) throws RemoteException;
	String getGroups() throws RemoteException;
	String removeGroup(int id) throws RemoteException;
}

