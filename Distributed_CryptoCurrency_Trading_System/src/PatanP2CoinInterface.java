import java.rmi.*;
public interface PatanP2CoinInterface extends Remote {
	public String getName() throws RemoteException;
	public void setName(String name) throws RemoteException;
	public String getAbbreviatedName()  throws RemoteException;
	public void setAbbreviatedName(String abbreviatedName) throws RemoteException;
	public String getDescription()  throws RemoteException;
	public void setDescription(String description) throws RemoteException;
	public long getMarketcap() throws RemoteException;
	public void setMarketcap(long marketcap) throws RemoteException;
	public long getTradingVolume() throws RemoteException;
	public void setTradingVolume(long tradingVolume) throws RemoteException;
	public float getOpeningPrice()  throws RemoteException;
	public void setOpeningPrice(float openingPrice) throws RemoteException;
	public String getTimestamp() throws RemoteException;
	public void setTimestamp(String timestamp) throws RemoteException;
}
