
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.*;

public interface PatanP2CryptoCoinServicesInterface extends Remote{
	//public void viewCoins() throws RemoteException;
	public  boolean   buy(String coinAbbreviation, float price, String username) throws RemoteException , FileNotFoundException, IOException; 
	public  boolean  sell(String coinAbbreviation, float price, String username) throws RemoteException , FileNotFoundException, IOException;
	public boolean autheticationCheck(String username, String password) throws IOException, RemoteException;
	public PatanP2ClientModel getClientStateFromFiles(String clientID) throws RemoteException;
	// public boolean  storeClientState(String clientID, PatanP2ClientModel clientState) throws RemoteException, FileNotFoundException, IOException;
}
