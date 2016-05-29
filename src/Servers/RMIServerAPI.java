package servers;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerAPI extends Remote {
	// GROUPS SERVICES
	String getGroups(String token) throws RemoteException;

	String deleteGroup(String nameGroup, String token) throws RemoteException;

	String postGroup(int id, String name, String desc, String userName, String jsonUsers, String token)
			throws RemoteException;
	
	String setUsersGroup(String groupName, String jsonUsers, String token) throws RemoteException;
	
	void writeGroupsFile() throws RemoteException;
	
	void loadGroupsFile() throws RemoteException;
	
	// USERS SERVICES
	String getUsers() throws RemoteException;

	String deleteUser(String userName, String token) throws RemoteException;

	String getUser(String userName, String token) throws RemoteException;

	String postUser(int id, String name, String email) throws RemoteException;

	void writeUsersFile() throws RemoteException;
	
	void loadUsersFile() throws RemoteException;
	
	// LOGS SERVICES

	String getLogs() throws RemoteException;

	String deleteLog(String LogDateTime) throws RemoteException;

	String postLog(String userName, String token, String dateTime) throws RemoteException;

	void writeLogsFile() throws RemoteException;
	
	void loadLogsFile() throws RemoteException;

}
