
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Project1Advanced extends JFrame{
	
	//Declaring the necessary variables
	String playerName, currentPlayer, playerOneTics = "", computerTics = "", winner = "";
	int playerWins, playerLosses, computerWins, computerLosses;
	JButton[] ticTacBtns = new JButton[9];
	//An array containing all the correct patterns for a winning game
	String[] correctPatterns = {"123", "147", "258", "369", "456", "789", "159", "357"}; 
	JPanel topPanel = new JPanel();

	
	
	//Function name: createUserInformationPanels()
    //Input: String label - what the panel should describe the textfield as
	//int wins, int loss - the variable to store all the wins and losses with
	//String playerUserName, boolean isComputer -> Checking whether the player is a human or a computer
    //Output: JPanel
    //Purpose: Builds the two top panels showing user and computer information
	public JPanel createUserInformationPanels(String label, int wins, int loss, String playerUserName, boolean isComputer) {
		JPanel panel = new JPanel();
		JLabel panelLabel = new JLabel(label);
		JLabel panelWins = new JLabel("Wins");
		JLabel panelLosses = new JLabel("Loses");
		JLabel panelWinsCount = new JLabel(""+wins);
		JLabel panelLossesCount = new JLabel(""+loss);
		panel.add(panelLabel);
		
		//If the panel is being created for a computer, the text field should not be editable
		if(!isComputer) {
			JTextField panelTextField = new JTextField(15);
			panelTextField.setText(playerUserName);
			panel.add(panelTextField);
		}else {
			JLabel panelComputerText = new JLabel("Computer Mina");
			panel.add(panelComputerText);
		}
		
		//Use a grid layout and add the wins and losses
		panel.setLayout(new GridLayout(3,2));
		panel.add(panelWins);
		panel.add(panelWinsCount);
		panel.add(panelLosses);
		panel.add(panelLossesCount);

		return panel;
	}
	
	
	//Function name: threatHasBeingDealtWith()
    //Input: String currentPattern -> the possible winning pattern of the human player
    //Output: Boolean -> if the computer has already blocked the players move or not
    //Purpose: Makes sure that the computer does not try to block a move that it has already blocked
	public boolean threatHasBeingDealtWith(String currentPattern) {
		for(int i = 0; i < currentPattern.length(); i++){
			int computerReacts = (currentPattern.charAt(i) - '0') - 1;
			if(!playerOneTics.contains(""+currentPattern.charAt(i)) && ticTacBtns[computerReacts].getText() == "") {
				return false; 
			}
		}
		return true;
	}
	
	//Function name: chooseRandomTileComputer()
    //Input: none
    //Output: Integer
    //Purpose: Chooses a random tile for the computer to play, uses a recursive function if the spot is taken
	public int chooseRandomTileComputer() {
		double randomNumber = Math.floor(Math.random() * 9);
		if(ticTacBtns[(int)randomNumber].getText() != "") {
			randomNumber = chooseRandomTileComputer();
		} 	
		return (int)randomNumber;
	}
	
	//Function name: handleWinner()
    //Input: none
    //Output: none
    //Purpose: If there is a winner, update the UI and let the player know
	public void handleWinner() {
		if(winner == "Computer") {
			computerWins++;
			playerLosses++;
		}else {
			playerWins++;
			computerLosses++;
		}
		topPanel.removeAll();
		topPanel.add(createUserInformationPanels("Player 1 ( X ): ", playerWins, playerLosses, playerName, false));
		topPanel.add(createUserInformationPanels("Computer ( O ): ", computerWins, computerLosses, "Comp. Bob", true));
		
		//Disables the board
		for(int i = 0; i < 9; i++) {
			ticTacBtns[i].setText("");
			ticTacBtns[i].setEnabled(false);
		}
		
	    //Check if the winner is the human or not, and reacts accordingly
		if(winner == playerName) {			
			JOptionPane.showMessageDialog(null,winner + " has won this match!");
		}else {
			JOptionPane.showMessageDialog(null,"You just lost against a computer...tsk, tsk, tsk");
		}
		
		//Resets the winnner, computer and player's tics
		computerTics = "";
		playerOneTics = "";
		winner = "";
		
		//Chooses a random player to go first
		double randomNumber = Math.floor(Math.random() * 10);
		if(randomNumber % 2 == 0) {
			currentPlayer = "Computer";
		}else {
			currentPlayer = playerName;
		}
		
		JOptionPane.showMessageDialog(null, currentPlayer + " is going to start this game! Let's go!  ");
		
		if(currentPlayer == "Computer") {
			controlComputer();
		}

		for(int i = 0; i < 9; i++) {
			ticTacBtns[i].setEnabled(true);
		}
	}

	//Function name: checkIfPossibleWinIsPossible()
    //Input: String possibleWinPattern -> pattern that the human can win with
    //Output: none
    //Purpose: Check if the human can win with a pattern
	public boolean checkIfPossibleWinIsPossible(String possibleWinPattern) {
		for(int i = 0; i < 3; i++) {			
			//converts the possible win pattern from a char to a number
			int position = possibleWinPattern.charAt(i) - '0';
	
			if(ticTacBtns[position - 1].getText() == "X") {
				return false;
			}
		}	
		return true;
	}

	//Function name: chooseTileComputer()
    //Input: String computerTics 
    //Output: Int -> Where the computer can go to win
    //Purpose: Uses the computer's current tics to see if it can win
	public int chooseTileComputer(String computerTics) {
		//When not defending attack, if no opportunity for winning, choose random tile
		boolean winningMove= false;
		int occurences = 0, winningMoveSpot = 0;
		String possibleWinPattern = "";
		
		//Checks for a winning pattern
		for(int pattern = 0; pattern < correctPatterns.length; pattern++) {
			for(int j = 0; j < computerTics.length(); j++) {
				if(correctPatterns[pattern].contains(""+computerTics.charAt(j))) {
					occurences++;
				}
			}		
			
			if(occurences == 2 && checkIfPossibleWinIsPossible(correctPatterns[pattern])) {
				possibleWinPattern = correctPatterns[pattern];
				break;
			}		
			
			occurences = 0;			
		}
		
		//If there is a winning move, then return the spot to win
		for(int i = 0; i < possibleWinPattern.length(); i++){
			int computerReacts = (possibleWinPattern.charAt(i) - '0') - 1;
			//If the human hasn't played in the spot
			if(!playerOneTics.contains(""+possibleWinPattern.charAt(i))&& ticTacBtns[computerReacts].getText() == "") {
				winningMoveSpot = computerReacts;
				winningMove = true;
			}
		}
		
		
		//return winning move number	
		if(!winningMove) {
			return chooseRandomTileComputer();
		} else {
			return winningMoveSpot;
		}
		
	}
	
	//Function name: boardIsNotFull()
    //Input: None
    //Output: Boolean -> if the board is not full, return true
    //Purpose: Checks whether the board is empty
	public boolean boardIsNotFull() {
		for(int i = 0; i < 9; i++) {
			if(ticTacBtns[i].getText() == "") {
				return true;
			}
		}
		
		System.out.println("Board is full");
		return false;
	}
	
	//Function name: chooseTileComputer()
    //Input: String computerTics 
    //Output: Int -> Where the computer can go to win
    //Purpose: Uses the computer's current tics to see if it can win
	public boolean checkForAWinner(String playerTics) {
		boolean userHasWon = false;
		int occurences = 0;
		for(int pattern = 0; pattern < correctPatterns.length; pattern++) {
			for(int j = 0; j < playerTics.length(); j++) {
				if(correctPatterns[pattern].contains(""+playerTics.charAt(j))) {
					occurences++;
				}
			}			
			if(occurences == 3) {
				userHasWon = true;
				break;
			}	
			occurences = 0;			
		}
		return userHasWon;
	}
	
	//Function name: controlComputer()
    //Input: None
    //Output: None
    //Purpose: If the middle spot is empty, the computer takes it, or else, 
	public void controlComputer() {	
		String currentPattern = "";
		boolean dangerSpotted = false;
		int humanPlayerOpportunity = 0;
		
		//If the empty space is taken, play a random spot
		if(ticTacBtns[4].getText() == "") {
			ticTacBtns[4].setText("O");
			computerTics+=5;
			currentPlayer = playerName;
		}
		
		if(currentPlayer == "Computer" && winner != playerName) {		
		//If the user is about to make a winning move, take it
		//Checking to make sure if the human has an opportunity to win
		for(int pattern = 0; pattern < correctPatterns.length; pattern++) {
			for(int playerTic = 0; playerTic < playerOneTics.length(); playerTic++) {
				//String[] correctPatterns = {"123", "147", "258", "369", "456", "789", "159", "357"}; 
				//If the current pattern contains parts of the players tic twice, add to the human opportunity
				if(correctPatterns[pattern].contains(""+playerOneTics.charAt(playerTic)) ) {
					humanPlayerOpportunity++;
				}
				
				if(humanPlayerOpportunity == 2 && !threatHasBeingDealtWith(correctPatterns[pattern])) {
					currentPattern = correctPatterns[pattern];
					dangerSpotted = true;
					break;
				}
			}
			
			//If the human can win, stop the loop and address it, or else, the loop continues with the next pattern
			if(dangerSpotted) {
				break;
			}else {						
				humanPlayerOpportunity = 0;
			}
		}
		
		//Takes the pattern that the user can win at, loops through it to see what's missing
		//And have the user play in that spot
		if(humanPlayerOpportunity != 0) {
			//check if computer can win without having to defend a win from player
			String testComputerTics = computerTics;
			int randomComputerPlay = chooseTileComputer(testComputerTics);
			System.out.println("Rnadom comp play - " + randomComputerPlay);
			testComputerTics += (randomComputerPlay + 1);
			//If the board is full, clear the board
			if(!boardIsNotFull()) {
				JOptionPane.showMessageDialog(null, "The board is full, clearing board");
				clearBoard();
			}
			
			if(checkForAWinner(testComputerTics)) {
				System.out.println("Comp can win withgout defending");
				computerTics += (randomComputerPlay + 1);
				ticTacBtns[randomComputerPlay].setText("O");;
		    	winner = "Computer";
		    	handleWinner();
			} else {
				for(int i = 0; i < currentPattern.length(); i++){
					int computerReacts = (currentPattern.charAt(i) - '0') - 1;
					if(!playerOneTics.contains(""+currentPattern.charAt(i))&& ticTacBtns[computerReacts].getText() == "") {
					    ticTacBtns[computerReacts].setText("O");
					    computerTics += computerReacts + 1;
					    if(checkForAWinner(computerTics)) {
					    	winner = "Computer";
					    	handleWinner();
					    };
					}
				}
			}

		
		} else {
			if(boardIsNotFull()) {	
				int randomComputerPlay = chooseTileComputer(computerTics);
				ticTacBtns[randomComputerPlay].setText("O");
				computerTics += randomComputerPlay + 1;
				if(checkForAWinner(computerTics)) {
			    	winner = "Computer";
			    	handleWinner();
				}
				
				System.out.println("Checking if board is full");
				if(!boardIsNotFull()) {
					JOptionPane.showMessageDialog(null, "The board is full, clearing board");
					clearBoard();
				}
			} else {
				JOptionPane.showMessageDialog(null, "The board is full, clearing board");
				clearBoard();
			}
		}

		currentPlayer = playerName;
	}
	}
	
	class AddTacListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton buttonClicked = (JButton)e.getSource();
			System.out.println("USER CLICKED, " + buttonClicked.getName());
				if(buttonClicked.getText() == "" && winner == "" && currentPlayer == playerName) {
					buttonClicked.setText("X");
//					buttonClicked.setForeground(Color.RED);
					playerOneTics+=buttonClicked.getName();
					if(checkForAWinner(playerOneTics)) {
						winner = playerName;
				    	handleWinner();
					};		
					
					if(!boardIsNotFull()) {
						JOptionPane.showMessageDialog(null, "The board is full, clearing board");
						clearBoard();
						
					}
				//change this to a function that is called automatically
				if(winner == "" && playerOneTics != "") {
					currentPlayer = "Computer";
					System.out.println("Controlling computer after the user has made their move");
					controlComputer();
					}
				}
		}
	};
	
	//Function name: clearBoard()
    //Input: none
    //Output: none
    //Purpose: Clear the board
public void clearBoard() {
			for(int i = 0; i < 9; i++) {
				ticTacBtns[i].setText("");
			}
			playerOneTics = "";
			computerTics = "";
	}
	

//Function name: buildMainGamePanel()
//Input: none
//Output: none
//Purpose: Start the game by building the game boards
public void buildMainGamePanel() {
		//Building top panel
//		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1,2));
		topPanel.add(createUserInformationPanels("Player 1 ( X ): ", playerWins, playerLosses, playerName, false));
		topPanel.add(createUserInformationPanels("Computer ( O ): ", computerWins, computerLosses, "Comp. Bob", true));
		add(topPanel,BorderLayout.NORTH);
		
		//Building middle panel
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new GridLayout(3,3));
		for(int i = 0; i < 9; i++) {
			ticTacBtns[i] = new JButton();
			ticTacBtns[i].setName(""+(i + 1));
			ticTacBtns[i].addActionListener(new AddTacListener());
			middlePanel.add(ticTacBtns[i]);
		}
		add(middlePanel,BorderLayout.CENTER);
		
		//Building bottom panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1,3));
		JButton resetButton = new JButton("Reset Board"), newGameButton = new JButton("New Game"), exitButton = new JButton("Exit");
		//Reset board
		resetButton.addActionListener(new ActionListener () {		
			public void actionPerformed(ActionEvent e) {	
				clearBoard();
			}
		});
		
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				playerWins = 0;
				playerLosses = 0;
				computerWins = 0;
				computerLosses = 0;	
				topPanel.removeAll();
				topPanel.add(createUserInformationPanels("Player 1 ( X ): ", playerWins, playerLosses, playerName, false));
				topPanel.add(createUserInformationPanels("Computer ( O ): ", computerWins, computerLosses, "Comp. Bob", true));
				computerTics = "";
				playerOneTics = "";
				currentPlayer = "";
				winner = "";
				for(int i = 0; i < 9; i++) {
					ticTacBtns[i].setText("");
				}
				
				double randomNumber = Math.floor(Math.random() * 10);
				if(randomNumber % 2 == 0) {
					currentPlayer = "Computer";
				}else {
					currentPlayer = playerName;
				}
				
				JOptionPane.showMessageDialog(null, currentPlayer + " is going to start this game! Let's go!  ");
				if(currentPlayer == "Computer") {
					System.out.println("Controlling computer from starting a new game button");
					controlComputer();
				}
			
			}
		});
		
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				System.exit(0);
			}
		});
		bottomPanel.add(resetButton);
		bottomPanel.add(newGameButton);
		bottomPanel.add(exitButton);
		add(bottomPanel, BorderLayout.SOUTH);
	}
	
//Constructor function
public Project1Advanced() {	
		setTitle("Tic Tac Foe");
		setSize(400, 400);
		setLayout(new BorderLayout(5, 0));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playerName = JOptionPane.showInputDialog("Please enter your name.");
		
		//Assigning the default name Player 1
		if(playerName.length() == 0) {
			playerName = "Player 1";
		}	
		
		JOptionPane.showMessageDialog(null,"Please be nice, the computer is still young");
		
		double randomNumber = Math.floor(Math.random() * 10);
		if(randomNumber % 2 == 0) {
			currentPlayer = "Computer";
		}else {
			currentPlayer = playerName;
		}
		
		JOptionPane.showMessageDialog(null, currentPlayer + " is going to start this game! Let's go!  ");
		System.out.println( currentPlayer + " is going to start this game!");
		buildMainGamePanel();
		
		if(currentPlayer == "Computer") {
			System.out.println("Controlling computer from constructor");
			controlComputer();
		}
			setVisible(true);
	}
	
public static void main(String[] args) {	
		new Project1Advanced();
	}
	
}
