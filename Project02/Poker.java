
// Author: Brenda Ebba
// File name: Poker.java
// Represents: For part 1 of the project my goal was to just connect my computer to the dealer's computer and also to make sure my commands were running correctly.
// Another portion for part 1 of the project was to check if my program can connect without the dealer's computer.




import java.util.Scanner; // Imports a scanner to read from the keyboard

import java.net.Socket; //Import for connecting to the dealer's server

import java.io.IOException; // Import for connecting to the dealer Server

import java.io.DataInputStream; // Import for connecting to the dealer's server

import java.io.DataOutputStream; // Import for connecting to the dealer's server

import java.io.*; // Imports all of io functions from the java library. I did this instead just incase I wasn't sure of the exact error catch in the try catch.

//Incorporate a string that changes the values for cards like into number values or something like that.



public class Poker{ // Class Poker that connects to the dealer's server wihtin the different methods within this class. 
					//This class also incorporates the play game method that will make the player's decisions after communicating to the dealer.

	DataOutputStream dos; // Instance variable for the DataOutputStream. It is an instance so it can call non-static methods.
	
	DataInputStream dis; // Instance variable for the DataInputStream. It is an instance variable so it can call non-static vmethods
	
	String dealerCommand; // Instance variable dealer command which forms a string out of the read method which contains all the Dealer's commands for the game.
	
	static boolean readFromFile; // Instance variable for the variable readFromFile in order to be used throughout the class Poker.
	
	static Scanner scan; // Instance variable for the the scanner in order for scan to be used throughout the class Poker.
	
	static Socket socket; // Instance variable for the socket which will allow me to access and use this variable throughout the calss.

	static String holeCard; //Instance variable for my face down card so I can use it within the class

	static String myUpCard1; //Instance variable for my first card facing up

	static String myUpCard2; //Instance variable for my second facing up card.

	static int myChips; //Instance variables for the amount of chips I have.

	static int sizeOfPot; //Instance variables for the size of the pot.

	static int currentBet; //Instance variables for the current bet.

	static String opponentsCards; //Instance variable for my opponents cards so I can compare them with mine.

	


	private void write(String s) throws IOException{ // This method writes to the dealer the player's decisions to the dealer.
		
		if(readFromFile == false){ //If im not reading a file then this block of code will write to the socket instead.
			dos.writeUTF(s);
			dos.flush();
		}
		System.out.println("\twrite: " + s); // Prints out "s" to the terminal. "s" is basically what I wrote back to the socket.
		
	
	}

	

	private String read() throws IOException{ // If i'm not reading a file then this method will read to the socket and if I am reading from a file then I'm going to read s  to the file "readFromFile".

		
		if(readFromFile == false){ 
			dealerCommand = dis.readUTF();
		}else{
			dealerCommand = scan.nextLine();
		}
		System.out.println("\tread: " + dealerCommand);
		return dealerCommand;



	}

	public static void main(String[] args){ // This is the main method which will run a try catch case where this method will try to create a new Poker object reference by the name player. 
											// This class will also create a new socket object and a new data input stream, and data output tsream object which will then call to the playGame method. 
											// If the network cannot connect to the dealer's network then it will print Host not found.
		try{
			Poker player = new Poker(); // This part creates a new Pocker object "player" where I can access the playGame method.

			if(args.length == 1){ //If I am reading from "readFromFile.txt" then the "readFromFile" variable is actually true.
				
				readFromFile = true;
				
				try{
					File file = new File("readFromFile.txt"); //If it's true then i'm going to make a new file object where I can read that file.
					 scan = new Scanner(file); // Setting my scanner to scan the file.
					
				}catch(IOException e1){
					System.out.println("Error"); //If i can't find the file then it will print error.
				}

			
			}else if(args.length == 2){ // If I'm reading to an IP and port then readFromFile will be false.
				
				readFromFile = false;
				
				socket = new Socket(args[0],Integer.parseInt(args[1]));  //Creating a new socket obect
				player.dis = new DataInputStream(socket.getInputStream());
				player.dos = new DataOutputStream(socket.getOutputStream());
			}


			// This block of code is going to allow me to create a new  socket object "socket" where it basically just connects to the Dealer's server or network.
			

			player.playGame(); // Calls the playGame method 

			if(player == null){ // If I have nothing left to bet then the socket will close. Only for when I'm testing without the dealer.
				socket.close(); // Once I return back to the main method from my playGame method. This line will close the socket if I should be kicked out of the game. Closes the socket after the "done" command.
			}
			
			

		}catch(IOException e1){
			System.out.println("Host not found"); // Prints host not found if the network cannot connect to the dealers noetwork.
		}

		
		
	}

	public void parts(){ //This method is created to separate my cards from the opponents cards in order to start my strategy and also to gather my pot, and current bet as well.

		String[] parts = dealerCommand.split("up");
		String firstPart = parts[0];
		String secondPart = parts[1];

		String[] firstPartColonSplit = firstPart.split(":");
		String[] secondPartColonSplit = secondPart.split(":");

		String myChipAmountString = firstPartColonSplit[1];
		 myChips = Integer.parseInt(myChipAmountString); //How many chips I have

		String sizeOfPotString = firstPartColonSplit[2];
		 sizeOfPot = Integer.parseInt(sizeOfPotString); //The size of the pot

		String currentBetString = firstPartColonSplit[3];
		 currentBet = Integer.parseInt(currentBetString); //Get the current bet


		myUpCard2 = firstPartColonSplit[6]; //My second card that is facing up

		holeCard = firstPartColonSplit[4]; //My card that is facing down

		myUpCard1 = firstPartColonSplit[5]; //My first card tha this facing up

		opponentsCards = secondPart; //My oponents cards.

		

	


	}




	public boolean hasThreeOfAKind(){ //This is to check if I have a three of a kind within my hand.

		String holeCardVal = holeCard.substring(0, holeCard.length() -1);
		String upValue1 = myUpCard1.substring(0, myUpCard1.length() -1);
		String upValue2 = myUpCard2.substring(0, myUpCard2.length() -1);


		return holeCardVal.equals(upValue1) && holeCardVal.equals(upValue2);

	}

	public boolean hasPair(){ // Checks to see if I have two cards with the same value

		String holeCardVal = holeCard.substring(0, holeCard.length() -1);
		String upValue1 = myUpCard1.substring(0, myUpCard1.length() -1);
		String upValue2 = myUpCard2.substring(0, myUpCard2.length() -1);

		return holeCardVal.equals(upValue1) || holeCardVal.equals(upValue2) || upValue1.equals(upValue2);

	}



	public int cardValue(String card){ //This method is what helps my betting, because it will give all of my cards their actual values, along with my opponents cards.

		String val = card.substring(0, card.length() -1);

		if(val.equals("A")){
			return 14;
		}else if(val.equals("K")){
			return 13;
		}else if(val.equals("Q")){
			return 12;
		}else if(val.equals("J")){
			return 11;
		}else{
			return Integer.parseInt(val);
		}
	}


	public boolean hasHighSpadeInHole(){ //This method checks if my card facing down is a hgih spade.

		return holeCard.endsWith("S") && cardValue(holeCard) >= 10;

	}


	public boolean hasFaceCards(){ //This method checks to see if I have any face cards or not.

		if(cardValue(holeCard) >= 10 || cardValue(myUpCard1) >= 10 || cardValue(myUpCard2) >= 10){
			return true;
		}

		return false; //If I don't it will return false.
	}



	public boolean oppHasFaceCards(){ //This method checks to see if my opponent has any face cards.
		String[] othersCards = opponentsCards.split(":");
		for(int i = 0; i < othersCards.length; i++){
			if(othersCards[i].length() > 0 && cardValue(othersCards[i]) >= 10){ //Ignores empty strings while checking if the opponent has a face card.
				return true;
			}
		}

		return false;
	}



	public boolean isOppSpadeHigher(){ //This method checks to see if my opponent has a higher spade than me

		String[] othersCards = opponentsCards.split(":");
		for(int i = 0; i < othersCards.length; i++){
			if(othersCards[i].length() > 0 && othersCards[i].endsWith("S") && cardValue(othersCards[i]) > cardValue(holeCard)){ //Again ignoring empty strings as well to avoid runtime errors.
				return false; //If they do then the method will return false.
			}
		}

		return true; //If they do then return true.
	}
	

	public boolean opponentHasPair(){ //This checks to see if my opponents have a pair.

		String[] othersCards = opponentsCards.split(":");

		String cardVal1;
		String cardVal2;
		for(int i = 0; i < othersCards.length; i += 2){

			if(othersCards[i].length() > 0 ){
				cardVal1 = othersCards[i].substring(0, othersCards[i].length() - 1);
				cardVal2 = othersCards[i+1].substring(0, othersCards[i+1].length() -1);
				if(cardVal1.equals(cardVal2)){
					return true; //If they do then it should return true.
				}
			}
			
				
			
		}

		return false; //If not then it will return false.
	}




	public String decidePlay(){ //Decides how i'm going to play depending on my strategies.

		if(hasThreeOfAKind() == true){ // If i have a three of a kind then i'm going to add 10 to the current bet.

			return "bet:" + (currentBet + 10);

		}else if(hasPair() == true && !opponentHasPair()){ //If I have a pair and the opponent doesn't then I'll add 5 to the current bet.

			return "bet:" + (currentBet + 5);

		}else if(hasPair() && opponentHasPair()){ //If I have a pair and the opponent has a pair then i'll just match the current bet.

			return "bet:" + currentBet;

		}else if(isOppSpadeHigher() == false){ //If the opoent doesn't have a higher spade than me then I'll raise the bet by 10.

			return "bet:" + (currentBet + 10);

		}else if(hasFaceCards() && oppHasFaceCards()){ //If I have a face card and the opponent has a face card as well then I'll match the current bet.
			
			return "bet:" + (currentBet);

		}else if(hasFaceCards() && !oppHasFaceCards()){ //If I ahve a face card and the opponent doesn't then I'll raise the current bet by 5.

			return "bet:" + (currentBet + 5);

		}else{ //If anything else which wouldn't be in my favor then i'll end up folding.
			return "fold";
		}

	}



	public void playGame(){ //This method is how I'm responding back to the socket to play the game. 



		try{


			dealerCommand = read(); // This will read in which commands the dealer is telling me to do or asking me to do.

			while(!dealerCommand.startsWith("done")){ // This loop will run until the dealer tells me i'm done with the game.

				if(dealerCommand.startsWith("login")){ // If the dealer tells me to login then I will write to the dealer my GithubID and my username for the game.
				write("Brendae08:player1234");


				}else if(dealerCommand.startsWith("bet1")){ // If the dealer commands bet 1. Then I will write back to the dealer my initial bet which is 1.
				write("bet:" + currentBet); //Writing back my ante bet which should be 1.
				

				}else if(dealerCommand.startsWith("bet2")){
				parts(); //Go to parts to separate and gather my cards, and to check to see what the opnenet has
				write(decidePlay()); //Uses my previous methods to see how I should bet and basically play the game. Writes back what I decide to play depending on my strategies.
		 
				}else if(dealerCommand.startsWith("status")){ // If the dealer commands me to import my status I will then just call the dealerCommand which is whatever hand and money I have left and show it to all servers.
					System.out.println(dealerCommand); // Prints whatever hand and money amount I had left.
				}
 

				dealerCommand = read(); // This will just read the dealer command which will just again show my final hand and money amount.

			}
			 if(dealerCommand.startsWith("done")){ // If the dealer says that I'm done with the game and I lost or won or cheated I will automatically just show my final amount and hand.
				System.out.println(dealerCommand);
				return; // Returns back to the main method.
			}

		}catch(IOException e1){
			System.out.println("Error"); //If any errors are catched then my terminal will print out error.
		}
	
	}

	
}

	
	