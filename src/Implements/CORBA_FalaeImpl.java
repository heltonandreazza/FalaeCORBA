package Implements;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import servers.RMIServerAPI;
import servers.ServerUtils;
import token.TokenGenerator;
import Corba.CORBA_FalaePOA;

public class CORBA_FalaeImpl extends CORBA_FalaePOA {

	private RMIServerAPI stub;

	public CORBA_FalaeImpl() throws RemoteException, NotBoundException {
		super();
		this.stub = ServerUtils.getServerStub();
	}

	// RMI METHODS
	@Override
	public String getUsers() {
		// TODO Auto-generated method stub
		try {
			return stub.getUsers();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String postLog(String userName, String token, String dateTime) {
		// TODO Auto-generated method stub
		try {
			return stub.postLog(userName, token, dateTime);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getLogs() {
		// TODO Auto-generated method stub
		try {
			return stub.getLogs();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// CORBA METHODS
	@Override
	public String generateToken(String userName, String userPassword) {
		// TODO Auto-generated method stub
		return TokenGenerator.generateToken(userName, userPassword);
	}

	@Override
	public boolean verifyToken(String token) {
		// TODO Auto-generated method stub
		return TokenGenerator.verifyToken(token);
	}
}