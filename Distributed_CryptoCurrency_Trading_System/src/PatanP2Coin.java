import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class PatanP2Coin  extends UnicastRemoteObject implements PatanP2CoinInterface   {
	private String name;
	private String abbreviatedName;
	private String description;
	private long marketcap;
	private long tradingVolume;
	private float openingPrice;
	private String timestamp;
	public PatanP2Coin(String name, String abbreviatedName, String description,
			long marketcap, long tradingVolume, float openingPrice,
			String timestamp) throws RemoteException {
		
		this.name = name;
		this.abbreviatedName = abbreviatedName;
		this.description = description;
		this.marketcap = marketcap;
		this.tradingVolume = tradingVolume;
		this.openingPrice = openingPrice;
		this.timestamp = timestamp;
	}
	public String getName() throws RemoteException{
		return name;
	}
	public void setName(String name) throws RemoteException{
		this.name = name;
	}
	public String getAbbreviatedName()  throws RemoteException{
		return abbreviatedName;
	}
	public void setAbbreviatedName(String abbreviatedName) throws RemoteException  {
		this.abbreviatedName = abbreviatedName;
	}
	public String getDescription()  throws RemoteException{
		return description;
	}
	public void setDescription(String description) throws RemoteException {
		this.description = description;
	}
	public long getMarketcap() throws RemoteException {
		return marketcap;
	}
	public void setMarketcap(long marketcap) throws RemoteException {
		this.marketcap = marketcap;
	}
	public long getTradingVolume() throws RemoteException{
		return tradingVolume;
	}
	public void setTradingVolume(long tradingVolume) throws RemoteException{
		this.tradingVolume = tradingVolume;
	}
	public float getOpeningPrice()  throws RemoteException{
		return openingPrice;
	}
	public void setOpeningPrice(float openingPrice) throws RemoteException  {
		this.openingPrice = openingPrice;
	}
	
	public String getTimestamp() throws RemoteException{
		return timestamp;
	}
	public void setTimestamp(String timestamp) throws RemoteException{
		this.timestamp = timestamp;
	}
	
	
	
	

}
