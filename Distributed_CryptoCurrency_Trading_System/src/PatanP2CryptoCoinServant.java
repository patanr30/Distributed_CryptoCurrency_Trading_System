import java.util.*;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.server.*;

public class PatanP2CryptoCoinServant extends UnicastRemoteObject implements PatanP2CryptoCoinServicesInterface {
	
	private static Map<String, Object> coins = new HashMap<String, Object>();
	private  Map<String,String> userList = new HashMap<String,String>();
	private String username;


	public PatanP2CryptoCoinServant() throws RemoteException {
		//default constructor
	}

	 
	 
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}

	 
	/********************************************************************
	  * @Method: addCoin
	  * @purpose: adds Coin to the Server data structure
	  *******************************************************************/

	public void addCoin(String abbreviatedName, PatanP2Coin coin){
			 coins.put(abbreviatedName, coin);
			 System.out.println("\nCoin "+abbreviatedName+" added succesfully\n");
	 }
		
	/********************************************************************
	  * @Method: removeCoin
	  * @purpose: removes Coin from the Server data structure
	  *******************************************************************/
	 public void removeCoin(String abbreviatedName){
		    coins.remove(abbreviatedName);
		    System.out.println("\nCoin "+abbreviatedName+" removed succesfully\n");
	 }
	 
	/********************************************************************
	  * @Method: unbinder
	  * @purpose: unbindes Coin from the url
	  *******************************************************************/ 
	 public void unbinder(int coinIndex) throws RemoteException, MalformedURLException, NotBoundException{
		   String url = "rmi://localhost/"+"coin"+String.valueOf(coinIndex);
		   Naming.unbind(url);
	   }
	 
	
	 /********************************************************************
	  * @Method: editCoin
	  * @purpose: edits coin
	  * @throws RemoteException
	  *******************************************************************/ 
	 public void editCoin(String abbreviatedName, long volume ) throws RemoteException{
		 PatanP2Coin coin = (PatanP2Coin)coins.get(abbreviatedName);
		 coin.setTradingVolume(volume);
		 coins.put(abbreviatedName, coin);
		 System.out.println("\nCoin "+abbreviatedName+" edited succesfully\n");
	 }
	 
	 
	 /*****************************************************************
	  * @Method: getCoins
	  * @purpose: to get the coins information created by server
	  ****************************************************************/ 
	 public ArrayList getCoins(){
		 ArrayList<PatanP2Coin> coinslist = new ArrayList<PatanP2Coin>();
		 if(coins!=null){
			 
			 for(Map.Entry<String, Object> entry: coins.entrySet()){
				 PatanP2Coin coin = (PatanP2Coin)(entry.getValue());
				 coinslist.add(coin);
			 }
		 }else{
			System.out.println("\nSorry!! No coins availble\n");
		 }
		return coinslist; 
	 }
	 
	 
	 /*************************************************************
	  * @Method: buy
	  * @purpose: adds bought coin to client list of coins
      *           adds trading price from the client's purchase power
      *           updates the clientState
      *           updates the Coin info, subtracts the tading price from the voulme	  
	  * @return true upon success 
	  * @throws RemoteException, FileNotFoundException, IOException
	  ****************************************************************/
	 public synchronized boolean  buy(String coinAbbreviation, float price, String username) throws RemoteException, FileNotFoundException, IOException{
		 
		 if(coins.containsKey(coinAbbreviation)){
			 
			 PatanP2Coin coin = (PatanP2Coin)coins.get(coinAbbreviation);
			 
			 if(coin.getOpeningPrice()== price){
				 System.out.println("Username "+username+" Entered prices are equal");
				 PatanP2ClientModel clientState = this.getClientStateFromFiles(username);
				 System.out.println("Username "+username+" ClientState"+ clientState);
				 if(clientState==null){
					 ArrayList<PatanP2CoinInterface> coins = new ArrayList<PatanP2CoinInterface>();
					 clientState = new PatanP2ClientModel(username,username,0,coins);
					 System.out.println("Username "+username+" Created Client state");
				 }
				 
				
				 ArrayList<PatanP2CoinInterface> clientCoins = clientState.getCoins();
				 
				 PatanP2CoinInterface serverCoin = (PatanP2CoinInterface)PatanP2CryptoCoinServant.coins.get(coinAbbreviation);
				 //Addes purchased coin to the client
				 clientCoins.add(serverCoin);
				 System.out.println("Username "+username+" added coin to client");
				 
				//Updating the volume -- for buy operation , subtracts trading amount to the volume of coin 
				 editCoin(coinAbbreviation,serverCoin.getTradingVolume()-(long)price );
				 System.out.println("Username "+username+" Edited the coin volume");
				
				 //Increase the purchase power of the client
				 float purchasePower =  (float)clientState.getPurchasePower();
				 clientState = new PatanP2ClientModel(username,username,purchasePower+price,clientCoins);
				 System.out.println("Username "+username+" updated the purchase power");
				 //Update Client state
				 storeClientState(username,clientState);
				 return true;
				 
			 }
			 
		 }
		 
		 System.out.println("Username "+username+" test4");
		 
		 
		return false; 
		 
	 }
	 
	 /*************************************************************
	  * @Method: sell
	  * @purpose: removes sold coin from client list of coins
      *           subtracts trading price from the client's purchase power
      *           updates the clientState
      *           updates the Coin info, adds the trading price to the volume	  
	  * @return true upon success 
	  * @throws RemoteException, FileNotFoundException, IOException
	  *****************************************************************/
		 public synchronized boolean  sell(String coinAbbreviation, float price, String username) throws RemoteException, FileNotFoundException, IOException{
		 
		 if(coins.containsKey(coinAbbreviation)){
			 
			 PatanP2Coin coin = (PatanP2Coin)coins.get(coinAbbreviation);
			 
			 if(coin.getOpeningPrice()== price){
				 System.out.println("Username "+username+" Entered prices are equal");
				 
				 PatanP2ClientModel clientState = this.getClientStateFromFiles(username);
				 System.out.println("Username "+username+"ClientState "+ clientState);
				 if(clientState==null){
					 ArrayList<PatanP2CoinInterface> coins = new ArrayList<PatanP2CoinInterface>();
					 clientState = new PatanP2ClientModel(username,username,0,coins);
					 System.out.println("Username "+username+" Created client state");
				 }
				 
				
				 ArrayList<PatanP2CoinInterface> clientCoins = clientState.getCoins();
				
			      boolean hasSold = false;
				  for(PatanP2CoinInterface client_coin :clientCoins){
					  if(client_coin.getAbbreviatedName().equalsIgnoreCase(coinAbbreviation)){
						//1. If the client has the coin which he is selling, remove that coin from the client list
						  clientCoins.remove(clientCoins.indexOf(client_coin));
						  System.out.println("Username "+username+" Removed coin from client data");
						  //2. Updating the volume -- for sell operation , adds trading amount to the volume of coin 
						  PatanP2CoinInterface serverCoin = (PatanP2CoinInterface)PatanP2CryptoCoinServant.coins.get(coinAbbreviation); 
						  editCoin(coinAbbreviation,serverCoin.getTradingVolume()+(long)price );
						  System.out.println("Username "+username+" Edited coin data i.e voulme in server");
						  hasSold = true;
						  break;
						
					  }
					  
				  }
				  
				  if(hasSold){
					  //3. Decrease the purchase power of the client
						 float purchasePower =  (float)clientState.getPurchasePower();
						 clientState = new PatanP2ClientModel(username,username,purchasePower-price,clientCoins);
						 System.out.println("Username "+username+" updated the purchase power of the client");
						 storeClientState(username,clientState);
						 return true;
				  }
				

				 
			 }
			 
		 }
		 
		 System.out.println("Username "+username+" test4.. Sell");
		 
		 
		return false; 
		 
	 }
	 
		 /******************************************************************
		  * @Method: getClientStateFromFiles
		  * @purpose: Identifies and deserializes data from clientID+”ser”
		  * @return true upon success 
		  * @throws RemoteException
		  *****************************************************************/

    	 public PatanP2ClientModel getClientStateFromFiles(String clientID) throws RemoteException{
			 FileInputStream fileIn;
			 PatanP2ClientModel clientState = null;
			try {
				fileIn = new FileInputStream(clientID+".ser");
			
				ObjectInputStream in =  new ObjectInputStream(fileIn);
				clientState = (PatanP2ClientModel)in.readObject();
				 in.close();
				 fileIn.close();
			} catch (FileNotFoundException e) {
				clientState = null;
	   			System.out.println("Username "+clientID+" Client file not found");
			} catch (IOException e) {
				System.out.println("Username "+clientID+" Unable to close the  opened file");
				//e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("Username "+clientID+" Class  ot found exception in PatanP2ClientService ");
			}
			
			return clientState;
			
		 }
		 
    	 /*************************************************************
		  * @Method: storeClientState
		  * @purpose: serializes data into clientID+”ser” in local directory
		  * @return true for successful serialization
		  * @throws FileNotFoundException IOException
		  *****************************************************************/

		 public boolean  storeClientState(String clientID, PatanP2ClientModel clientState) throws  FileNotFoundException, IOException{
			 if(clientState != null){
				 
				 //File file = new File("\tmp\\"+clientID+".ser");
				 File file = new File(clientID+".ser");
				 if(!file.exists()){
					file.createNewFile();	
					}
				 
				 FileOutputStream fileout = new FileOutputStream(clientID+".ser");
				
				 ObjectOutputStream out = new ObjectOutputStream(fileout);
				 out.writeObject(clientState);
				 out.close();
				 fileout.close();
				 System.out.println("Username "+clientID+" client state saved in server folder");
				 return true;
			 }
		  return false;
		 }
		 
		 
		 
		 
		/********************************************************************
		  * @Method: autheticationCheck
		  * @purpose: Checks user name  and password against userList.txt
		  *           and sends Success and fail acknowledgment to Sender 
		  * @return true for successful authentication
		  * @throws IOException
		  *******************************************************************/
		 
		 public boolean autheticationCheck(String username, String password) throws IOException, RemoteException{
			 //Get UserList data
			 getUserListMap();
			 String passwordFromList;
				
					if(!(username.equalsIgnoreCase("close"))){
						//Received message is not a Close message 
						if(userList != null){
							//If Valid Username is received
							if(userList.containsKey(username)){
								passwordFromList = userList.get(username);
									if(password.equals(passwordFromList)){
										this.setUsername(username);
										return true;
									}else{
										return false;
									}
								
							}else{
								return false;
							}
						}else{
							System.out.println("\n Unable to read UserList ... try again\n");
						}
					}
				
			 return false;
		 }
	
		 /********************************************************************
		  * @Method: getUserListMap
		  * @purpose: OPens userList.txt and get the content into HashMAp 
		  * @throws IOException
		  *******************************************************************/
		 
		 
		 private void getUserListMap() throws IOException{
			 BufferedReader br = null;
			 try {
				br = new BufferedReader(new FileReader("userList.txt"));
				String text ="";
				while((text= br.readLine())!= null){
					String substring[] = text.split(" ");
					userList.put(substring[0], substring[1]);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				br.close();
			}
		 }
		 


}
