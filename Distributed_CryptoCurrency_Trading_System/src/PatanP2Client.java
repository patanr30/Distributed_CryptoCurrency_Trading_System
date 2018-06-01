import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.io.*;

public class PatanP2Client {
	 private static  int CoinsCount = 5;
     private static String username;
     
     /*************************************************************
	  * @Method: main
	  * @purpose: provides user platform for Distributed Trading System
      ****************************************************************/ 	

	public static void main(String[] args)  {
		System.out.println("Welcome to Cryptocurrency Coin Application");
		BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
		
		try{

		boolean flag = true;
		
		while(flag){
			//Login
			System.out.println("Please Login to use the application");
			System.out.println("Username:");
			String username = br.readLine();
			System.out.println("Password:");
			String password = br.readLine();
			String url1 = "rmi://localhost/"+"servant";
			PatanP2CryptoCoinServicesInterface server = (PatanP2CryptoCoinServicesInterface)Naming.lookup(url1);
			flag = !server.autheticationCheck(username, password);
			if(flag){
				System.out.println("!!!! Invalid credentials !!!!");
			}else{
				PatanP2Client.username = username;
			}			
		}
		
		  System.out.println("Succesfully Logged In!!");
		  
		  boolean exit = true;
		  
			  while(exit){
			   
			   System.out.println("*********************Services***************************");
			   System.out.println("Press 1 to display coins information");
			   System.out.println("Press 2 to buy a coin");
			   System.out.println("Press 3 to sell a coin");
			   System.out.println("Press 4 to view your account details");
			   System.out.println("Press 5 to exit");
			   
			   int service = Integer.parseInt(br.readLine());
	
		
			   if(service== 1){
				   for(int i=0; i<CoinsCount; i++){
						String url = "rmi://localhost/"+"coin"+String.valueOf(i);
						PatanP2CoinInterface coin = (PatanP2CoinInterface)Naming.lookup(url);
						 System.out.println(coin.getAbbreviatedName()+ " Price: $"+coin.getOpeningPrice()+" Volume: $"+coin.getTradingVolume()+" MarketCap: $"+coin.getMarketcap());
					} 
			   }else if(service== 2){
				   String url1 = "rmi://localhost/"+"servant";
				   PatanP2CryptoCoinServicesInterface server = (PatanP2CryptoCoinServicesInterface)Naming.lookup(url1);
				   System.out.println("Enter the coin(abbreviation) you wish to buy");
				   String coinName = br.readLine();
				   System.out.println("Enter the amount");
				   float price = Float.valueOf(br.readLine()).floatValue();
				   System.out.println(price);
				   boolean result = server.buy(coinName,price,username);
				   System.out.println(result);
				   if(result){
					   System.out.println("Succesfully bought the coin!!");
				   }else{
					   System.out.println("Unsuccesfully in buying the coin!!"); 
				   }
				   System.out.println("BUY Servers"); 
			   }else if(service== 3){
				   String url1 = "rmi://localhost/"+"servant";
				   PatanP2CryptoCoinServicesInterface server = (PatanP2CryptoCoinServicesInterface)Naming.lookup(url1);
				   System.out.println("Enter the coin(abbreviation) you wish to sell");
				   String coinName = br.readLine();
				   System.out.println("Enter the amount");
				   float price = Float.valueOf(br.readLine()).floatValue();
				   System.out.println(price);
				   boolean result = server.sell(coinName,price,username);
				   System.out.println(result);
				   if(result){
					   System.out.println("Succesfully sold the coin!!");
				   }else{
					   System.out.println("Unable to sell the coin!!"); 
				   }
				   System.out.println("Sell Servers"); 
			   
			   }else if(service== 4){
				   String url1 = "rmi://localhost/"+"servant";
				   PatanP2CryptoCoinServicesInterface server = (PatanP2CryptoCoinServicesInterface)Naming.lookup(url1);
				   PatanP2ClientModel client = (PatanP2ClientModel) server.getClientStateFromFiles(username);
				   if(client!=null){
					   System.out.println("_____Username: "+username+"    Purchase: "+client.getPurchasePower());
					   ArrayList<PatanP2CoinInterface> coins = client.getCoins();
					   for(PatanP2CoinInterface clientCoin:coins){
						   System.out.println(clientCoin.getAbbreviatedName()+ " Price: $"+clientCoin.getOpeningPrice()+"Purchased date:"+ clientCoin.getTimestamp());
						   
					   }
				   }else{
					System.out.println("No Previous History available");   
				   }
				  
			   }else if(service== 5){
				  /* String url1 = "rmi://localhost/"+"servant";
				   PatanP2CryptoCoinInterface server = (PatanP2CryptoCoinInterface)Naming.lookup(url1);
				   boolean result = server.storeClientState(username);
				   if(result){
					   exit = false;
					   System.out.println("Session Stored!!");*/
				       System.out.println("Session Stored!!");
				       exit = false;
					   System.out.println("Thanks for using the application");
				 /*  }else{
					   System.out.println("Something went wrong, please try again");
				   }*/
				   
			   }else{
				   System.out.println("!!!!!! Invalid Input !!!!!!");
			   }
			  }
	   
	   }catch(RemoteException e){
		   e.printStackTrace();
		   System.out.println("Error in cleint");
	   } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (NotBoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		   
	  
	   }
		
   
}