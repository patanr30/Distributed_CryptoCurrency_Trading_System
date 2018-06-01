import java.io.Serializable;
import java.util.*;

/**
 * @author R
 *
 */
public class PatanP2ClientModel  implements Serializable {
	private String clientID;
	private String clientName;
	private float purchasePower;
	private ArrayList<PatanP2CoinInterface> coins;
	
	
	
	public PatanP2ClientModel(String clientID, String clientName, float purchasePower, ArrayList<PatanP2CoinInterface> coins) {
		//super();
		this.clientID = clientID;
		this.clientName = clientName;
		this.purchasePower = purchasePower;
		this.coins = coins;
	}

	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public double getPurchasePower() {
		return purchasePower;
	}
	public void setPurchasePower(float purchasePower) {
		this.purchasePower = purchasePower;
	}

	public ArrayList<PatanP2CoinInterface> getCoins() {
		return coins;
	}

	public void setCoins(ArrayList<PatanP2CoinInterface> coins) {
		this.coins = coins;
	}

	
	
	
	

}
