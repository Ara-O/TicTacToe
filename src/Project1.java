//Eyiara Oladipo
//Java Tic Tac Toe Project 1 
//10-11-2022
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

//Main class of Project1
public class Project1 extends JFrame {   
	
	//Declaring the necessary variables to keep track of user variables and panels 
	public static String[] correctPatterns = {"123", "147", "258", "369", "456", "789", "159", "357"}; 
	public static String playerOne = "", playerTwo = "";
	public static String playerOneTics = "", playerTwoTics = "";
	public static int playerOneWins = 0, playerOneLosses = 0, playerTwoWins = 0, playerTwoLosses = 0;
	public static String currentTurn;
	public static String winner;
	
	//Function name: createUserPanel()
    //Input: none
    //Output: JPanel
    //Purpose: Builds a customizable GUI top panel with the players name, wins and losses
	public static JPanel createUserPanel(String label, int wins, int loss, String playerUserName) {
		JPanel panel = new JPanel();
		JLabel panelLabel = new JLabel(label);
		JLabel panelWins = new JLabel("Wins");
		JLabel panelLosses = new JLabel("Loses");
		JLabel panelWinsCount = new JLabel(""+wins);
		JLabel panelLossesCount = new JLabel(""+loss);
		JTextField panelTextField = new JTextField(15);
		panelTextField.setText(playerUserName);
		panel.setLayout(new GridLayout(3,2));
		panel.add(panelLabel);
		panel.add(panelTextField);
		panel.add(panelWins);
		panel.add(panelWinsCount);
		panel.add(panelLosses);
		panel.add(panelLossesCount);

		return panel;
	}
	
	//Function name: buildMainFrame()
    //Input: none
    //Output: JFrame
    //Purpose: Builds the main game frame, with all the components necessary
	public static JFrame buildMainFrame() {
		//Initializing the necessary components and frames
		JFrame gameFrame = new JFrame();
		JPanel topPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		JButton[] ticTacBtns = new JButton[9];
		centerPanel.setLayout(new GridLayout(3,3));
		bottomPanel.setLayout(new GridLayout(1,3));
		topPanel.setLayout(new GridLayout(1,2));
		JButton newGameButton = new JButton("New Game");
		JButton resetButton = new JButton("Reset Button");
		JButton exitButton = new JButton("Exit");
				
		//Calls the createUserPanel, which returns a customizable panel - to avoid repeating code
		topPanel.add(createUserPanel("Player 1(O): ", playerOneWins, playerOneLosses, playerOne));
		topPanel.add(createUserPanel("Player 2(X): ", playerTwoWins, playerTwoLosses , playerTwo));
		gameFrame.setTitle("Tic Tac Toe");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(400, 400);
		gameFrame.setLayout(new BorderLayout(5, 0));
		gameFrame.add(topPanel,BorderLayout.NORTH);
		gameFrame.add(centerPanel,BorderLayout.CENTER);
		gameFrame.add(bottomPanel, BorderLayout.SOUTH);		

		//Adds an action listener to the reset button, creating a new game panel with new information
		resetButton.addActionListener(new ActionListener () {		
			public void actionPerformed(ActionEvent e) {
				gameFrame.dispose();
				playerOneWins = 0;
				playerTwoWins = 0;
				playerTwoLosses = 0;
				playerOneLosses = 0;
				playerOneTics = "";
				playerTwoTics = "";
				JFrame mainFrame = buildMainFrame();
				mainFrame.setVisible(true);
			}
		});
		
		//Looping through the ticTacButtons and resetting their values when new game is clicked
		newGameButton.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < 9; i++) {
					ticTacBtns[i].setText("");
				}
				playerOneWins = 0;
				playerTwoWins = 0;
				playerTwoLosses = 0;
				playerOneLosses = 0;
				playerOneTics = "";
				playerTwoTics = "";
				gameFrame.dispose();
				new Project1();
		}	

		});
		
		//Leaving the game by disposing the frame
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameFrame.dispose();
			}
		});
		
		bottomPanel.add(newGameButton);
		bottomPanel.add(resetButton);
		bottomPanel.add(exitButton);

		//Separate action listener to monitor the clicks of the tacs as the user plays
		class AddTacListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				//Gets the button that was clicked and the number associated with the button
				JButton buttonClicked = (JButton)e.getSource();
				//An if statement to make sure that repeatedly clicking the same button doesn't affect the game
				if(buttonClicked.getText() == "") {
					
			    //Based off the random user that starts, if the current turn is for player 1, set the button text to O
				if(currentTurn == playerOne) {
					buttonClicked.setText("O");
//					buttonClicked.setBackground(new Color());
					buttonClicked.setForeground(Color.RED);
					playerOneTics+=buttonClicked.getName();
					if(playerOneTics.length() > 2) {	
						//Check whether user has won using a function, if so, display the winning user as a jframe
						//and update the player scores
					    boolean userWon = checkPlayerTic(playerOneTics);
					    if(userWon) {
					    	gameFrame.setVisible(false);
					    	winner = playerOne;
					    	showWinningUser();
					    	playerOneWins++;
					    	playerTwoLosses++;
					    }
					}
					//Change the turn of the current player
					currentTurn = playerTwo;
					
					//Repeat, but if the current player is player 2, set the tics to X
				} else if(currentTurn == playerTwo) {
					buttonClicked.setText("X");
					buttonClicked.setForeground(Color.BLUE);
					playerTwoTics+=buttonClicked.getName();
					if(playerTwoTics.length() > 2) {	
					    boolean userWon = checkPlayerTic(playerTwoTics);
					    
					    if(userWon) {
					    	gameFrame.setVisible(false);
					    	winner = playerTwo;
					    	showWinningUser();
					    	playerTwoWins++;
					    	playerOneLosses++;
					    }
					}
					currentTurn = playerOne;
				}		
				}
				
				//If all the spaces are filled out, reset the board
				if(playerOneTics.length() + playerTwoTics.length() == 9) {
					
					for(int i = 0; i < 9; i++) {
						ticTacBtns[i].setText("");
					}
					playerOneTics = "";
					playerTwoTics = "";
				}
				
				System.out.println("player 1 tics: " + playerOneTics);
				System.out.println("player 2 tics: " + playerTwoTics);
				System.out.println("----------");

			}
		};

		//Adds the action listener to all the buttons
		for(int i = 0; i < 9; i++) {
			ticTacBtns[i] = new JButton();
			ticTacBtns[i].setName(""+(i + 1));
			ticTacBtns[i].addActionListener(new AddTacListener());
			centerPanel.add(ticTacBtns[i]);
		}

		return gameFrame;
	}
	
	//Function name: showWinningUser()
    //Input: none
    //Output: none
    //Purpose: Creates a GUI that shows to the players who won a match
	public static void showWinningUser() {
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(1, 2));
		frame.setSize(200,150);
		JPanel panel = new JPanel();

		JLabel label1 = new JLabel(winner + " has won!!!!");
		JLabel label2 = new JLabel(winner == playerOne ? playerTwo + " is the loser :(": playerOne + " is the loser :(");
		
		panel.add(label1);
		panel.add(label2);
		JButton anotherGameBtn = new JButton("Play another game");
		anotherGameBtn.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
					frame.dispose();
					startGame();
				}
			});
		
		panel.add(anotherGameBtn);
		frame.add(panel);
		frame.setVisible(true);
	}
	
	//Function name: chooseUserToBegin()
    //Input: none
    //Output: none
    //Purpose: Creates a random number and uses that to determine which user begins the game
	public static void chooseUserToBegin() {
		//Creates a frame to notify the user of which player is to begin
		JFrame frame = new JFrame();
		frame.setLayout(new GridLayout(1, 2));
		frame.setSize(200,150);
		JPanel panel = new JPanel();
		double randomNumber = Math.floor(Math.random() * 10);
		
		//When the user to go first has been selected, we can now start the game
		String chosenUser = randomNumber % 2 == 0 ? playerOne : playerTwo;
		currentTurn = chosenUser;
		JLabel label = new JLabel(chosenUser + " will begin this game");
		JButton startBtn = new JButton("Let's Start!");
		startBtn.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent e) {
					frame.dispose();
					//Start game
					startGame();
				}
			});
		
		panel.add(label);
		panel.add(startBtn);
		frame.add(panel);
		frame.setVisible(true);
	}
		
	//Function name: getUsers(String, int)
    //Input: A label of the text to show, int -> the current user putting in their information
    //Output: none
    //Purpose: Creates a customizable GUI that asks for a user's name
	public static void getUsers(String labelText, int userIndex) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JLabel label = new JLabel(labelText);
		JTextField getUserName = new JTextField(15);
		
		//An action listener that listens for the click of the Enter User button
		class addUser implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				//Gets the string from the getUserName textfield and sets the value to the
				//necessary player variable , if the user index is 1 - the first player, 
				//call the function to create another GUI for the second player
				//if user index is 2 for the second player, we call the chooseUserToBegin function, to randomly
				//Select who is going to start
				String userInput = getUserName.getText();
				if(userInput.length() == 0) {
					userInput = "Player" + userIndex;
				}
				if(userIndex == 1) {
					playerOne = userInput;
					getUsers("Please Enter Player 2: ", 2);
				} else if(userIndex == 2){
					playerTwo = userInput;
				}
				
				frame.dispose();
				
				if(userIndex == 2 ) {
					chooseUserToBegin();
				}
			}
		};
		
		//Initializing the panels and adding it to the frame
		JButton addUserBtn = new JButton("Enter User");
		addUserBtn.addActionListener(new addUser());
		frame.setSize(250,150);
		panel.add(label);
		panel.add(getUserName);
		panel.add(addUserBtn);
		frame.add(panel);
		frame.setVisible(true);
	};
	
	//Function name: checkPlayerTic(String)
    //Input: A string of all the current spots the player has clicked
    //Output: boolean for whether the user has matched 3 tics or not
    //Purpose: Creates a customizable GUI that asks for a user's name
	public static boolean checkPlayerTic(String playerTics) {
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
	
	//Function name: startGame()
    //Input: none
    //Output: none
    //Purpose: Resets the players tics, and builds the game panel
	public static void startGame() {
		System.out.println("starting game with users " + playerOne + " "  + playerTwo);
		playerOneTics = "";
		playerTwoTics = "";
		JFrame mainFrame = buildMainFrame();
		mainFrame.setVisible(true);	
	};
	
	//Constructor function
	public Project1() {
	   //Calls the get users function to get each player's name
		getUsers("Please Enter Player 1: ", 1);
	}
   
	//Main function
	public static void main(String[] args) {	
		//Initializing a new object of project 1 to get the game started
		new Project1();
	}
}
