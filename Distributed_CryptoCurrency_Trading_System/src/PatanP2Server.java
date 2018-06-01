import java.rmi.*;
import java.rmi.server.*;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;
public class PatanP2Server {
    private static  int CoinsCount = 5;
	 private static PatanP2Coin[] coins = new PatanP2Coin[CoinsCount];
	 private  static Map<String,Object> coinList = new HashMap<String,Object>();

	 /*************************************************************
	  * @Method: main
	  * @purpose: creates cryptocoins and provides login, sell, buy, login services
	  * @throws RemoteException
      ****************************************************************/ 

	public static void main(String[] args) throws RemoteException {
		try {
			
		 
			createCoin();
	
			PatanP2CryptoCoinServant servant = new PatanP2CryptoCoinServant();
			
			ArrayList<PatanP2Coin> list = servant.getCoins();
			int i=0;
			for(PatanP2Coin coin : list){
				coins[i] = coin;
				i++;
			}
			//Binding Server class
			String url1 = "rmi://localhost/"+"servant";
			Naming.rebind(url1, new PatanP2CryptoCoinServant());
			
			//Binding Coin Class
			for(int j=0; j<CoinsCount;j++){
				String url = "rmi://localhost/"+"coin"+String.valueOf(j);
				Naming.rebind(url, coins[j]);	
	
			}
			
			
			
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error in server");
		}
	}
	
	/********************************************************
	  * @Method: createCoin
	  * @purpose: creates Cryptocurrency coins 	
	  * @throws RemoteException
	  ***************************************************************/	
	public static void createCoin() throws RemoteException{
		PatanP2CryptoCoinServant servant = new PatanP2CryptoCoinServant();
	    PatanP2Coin btc = new PatanP2Coin("BITCOIN","BTC","BITCOIN",144281605710l,5446460000l,8525.77f, new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		servant.addCoin("BTC", btc);
		PatanP2Coin eth = new PatanP2Coin("ETHEREUM","ETH","ETHEREUM",60174824420L,1429600000L,612.59f, new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		servant.addCoin("ETH", eth);
		PatanP2Coin ltc = new PatanP2Coin("LITECOIN","LTC","LITECOIN",9449752582l,448359000l,169.78f, new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		servant.addCoin("LTC", ltc);
		PatanP2Coin maid = new PatanP2Coin("MAIDSAFECOIN","MAID","MAIDSAFECOIN",135708249l,912932l,0.299873f, new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		servant.addCoin("MAID", maid);
		PatanP2Coin neo = new PatanP2Coin("NEO","NEO","NEO",4547231000l,115780000l,69.96f, new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
		servant.addCoin("NEO", neo);
		System.out.println("Server succesfully added the coins");
	}
	


}

