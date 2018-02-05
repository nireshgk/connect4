package game;

import java.util.Scanner;

/**
 * Game Board class takes the input from the players.
 * Process the input as per the game rules
 */
public class GameBoard {

	/**
	 * Number of game board rows.
	 */
	private static final int NUMBER_OF_ROWS = 6;

	/**
	 * Number of game board columns.
	 */
	private static final int NUMBER_OF_COLUMNS = 7;

	/**
	 * Number of game board columns.
	 */
	private static final int MINIMUM_CHAIN_TO_WIN = 3;

	/**
	 * Player 1 uses red ('R')
	 */
	private BoardPlayer player1 = null;

	/**
	 * Player 2 uses green ('G')
	 */
	private BoardPlayer player2 = null;

	/**
	 * Current player in the game
	 */
	private BoardPlayer currentPlayer = null;

	/**
	 * Scanner to get input for the game from user
	 */
	Scanner scanner = null;

	/**
	 * Board for the game
	 */
	String[][] gameBoard;

	/**
	 * Start the game by setting the initial values
	 */
	public void startGame() {

		player1 = new BoardPlayer(1, "R", "RED");
		player2 = new BoardPlayer(2, "G", "GREEN");

		// set starting player turn
		currentPlayer = player1;

		gameBoard = generateEmptyBoard(NUMBER_OF_ROWS, NUMBER_OF_COLUMNS);
		printBoard();

	}
	
	/**
	 * play game
	 */
	public void playGame() {
		int turnNumber;

		boolean isGameOver = false;

		turnNumber = 1;
		// alternate the turn between player
		for (turnNumber = 1; !isGameOver; turnNumber++) {
			if (turnNumber % 2 == 0) {
				currentPlayer = player2;
			} else {
				currentPlayer = player1;
			}

			// Match info.
			System.out.println("_____________________________________\nTurn: " + turnNumber);

			// Drop disc into selected column
			dropDisc(getColumnFromPlayer() - 1);
			System.out.println();

			printBoard();

			// Check game board for winning conditions
			isGameOver = checkForWin();

		}
		System.out.println("_____________________________________");
		System.out.println("Player " + currentPlayer.id() + " [" + currentPlayer.discName() + "] wins");
	}

	/**
	 * End the game and cleanup
	 */
	public void endGame() {
		if (scanner != null)
			scanner.close();
	}
	
	/**
	 * Generates empty board
	 * 
	 * @param NUMBER_OF_ROWS
	 * @param NUMBER_OF_COLUMNS
	 * @return
	 */
	public static String[][] generateEmptyBoard(int NUMBER_OF_ROWS, int NUMBER_OF_COLUMNS) {
		int row, column;
		String[][] emptyBoard = new String[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];

		for (row = 0; row < emptyBoard.length; row++) {
			for (column = 0; column < emptyBoard[row].length; column++) {
				emptyBoard[row][column] = " ";
			}
		}
		return emptyBoard;
	}

	/**
	 * Prints the board
	 * 
	 * @param gameBoard
	 *            - game board to print
	 */
	public void printBoard() {
		int row, column, columnLabel;

		// Print each tile in the board
		for (row = 0; row < NUMBER_OF_ROWS; row++) {
			for (column = 0; column < NUMBER_OF_COLUMNS; column++) {
				System.out.print("|" + gameBoard[row][column]);
			}
			System.out.println("|");
		}
		System.out.print(" ");

		// Print column labels
		for (columnLabel = 1; columnLabel <= gameBoard[row - 1].length; columnLabel++) {
			System.out.print(columnLabel + " ");
		}
		System.out.println();
	}
    
	/**
	 * Drops disc into selected column.
	 * 
	 * @param column
	 * @return gameBoard
	 */
	public String[][] dropDisc(int column) {
		int row;

		outerLoop: for (row = NUMBER_OF_ROWS - 1; row >= -1; row--) {
			// If chosen column is full, asks for a different column
			if (row < 0) {
				System.out.println("Column " + (column + 1) + " is already full.");
				dropDisc(getColumnFromPlayer() - 1);
				break;
			}
			// Drop the disc into next available row of the selected column
			if (gameBoard[row][column].equals(" ")) {
				gameBoard[row][column] = currentPlayer.disc();
				break outerLoop;
			}
		}
		return gameBoard;
	}
    
	/**
	 * Get column choice from player
	 * 
	 * @return
	 */
	public int getColumnFromPlayer() {
		int intInput;

        scanner = new Scanner(System.in);

		// show prompt for current player
		System.out.print("Player " + currentPlayer.id() + " [" + currentPlayer.discName() + "] - choose column (1-" + NUMBER_OF_COLUMNS + "): ");

		outerLoop: while (true) {
			// If input is not integer, asks user again
			while (!scanner.hasNextInt()) {
				System.out.println("Your choice must be an integer. Try again!");
				scanner.next();
			}
			intInput = scanner.nextInt();

			// Saves input (if valid)
			if (intInput >= 1 && intInput <= NUMBER_OF_COLUMNS) {
				break outerLoop;
			} else {
				// If input is not valid column then asks user again.
				System.out.println("Your choice must be between 1 and " + NUMBER_OF_COLUMNS + ". Try again!");
				continue;
			}
		}
		return intInput;
    }

	/**
	 * Check game board for winning conditions
	 * 
	 * @param gameBoard
	 * @return
	 */
	public boolean checkForWin() {
		int column, columnsFull;
		boolean isGameOver;

		isGameOver = horizontalScan();
		if (isGameOver)
			return isGameOver;
		isGameOver = verticalScan();
		if (isGameOver)
			return isGameOver;
		isGameOver = diagonalScanDownLeft1();
		if (isGameOver)
			return isGameOver;
		isGameOver = diagonalScanDownLeft2();
		if (isGameOver)
			return isGameOver;
		isGameOver = diagonalScanDownRight1();
		if (isGameOver)
			return isGameOver;
		isGameOver = diagonalScanDownRight2();
		if (isGameOver)
			return isGameOver;

		// If the game board is full but neither player has won, the game is drawn.
		for (column = 0, columnsFull = 0; isGameOver != true && column < NUMBER_OF_COLUMNS; column++) {
			if (!gameBoard[0][column].equals(" ")) {
				columnsFull++;
				if (columnsFull >= NUMBER_OF_COLUMNS) {
					System.out.println("\nNo more discs can be inserted, game draw!");
					isGameOver = true;
				}
			}
		}

		return isGameOver;
	}
    
	/**
	 * Scanning in lines from left to right, check board for horizontal connects.
	 * Start checking at top-left and stop checking at bottom-left.
	 * 
	 * @return
	 */
	private boolean horizontalScan() {
		int row, column, maximumConnect;
		boolean isGameOver = false;

		outerLoop: for (row = 0, maximumConnect = 1; row < NUMBER_OF_ROWS; row++) {
			for (column = 0; column < NUMBER_OF_COLUMNS - 1; column++) {
				if (gameBoard[row][column].equals(currentPlayer.disc()) && gameBoard[row][column + 1].equals(currentPlayer.disc())) {
					maximumConnect++;
					// Check if Player won horizontally
					if (maximumConnect >= MINIMUM_CHAIN_TO_WIN) {
						currentPlayer.wins();
						System.out.println("\nPlayer " + currentPlayer.id() + " won horizontally!");
						isGameOver = true;
						break outerLoop;
					}
				} else {
					maximumConnect = 1;
				}
			}
			maximumConnect = 1;
		}
		return isGameOver;
	}
    
	/**
	 * Scanning in lines from top to bottom, check board for vertical connects.
	 * Start checking at top-left and stop checking at top-right.
	 * 
	 * @return
	 */
	private boolean verticalScan() {
		int row, column, maximumConnect;
		boolean isGameOver = false;

		outerLoop: for (row = 0, column = 0, maximumConnect = 1; column < NUMBER_OF_COLUMNS; column++) {
			for (row = 0; row < NUMBER_OF_ROWS - 1; row++) {
				if (gameBoard[row][column].equals(currentPlayer.disc()) && gameBoard[row + 1][column].equals(currentPlayer.disc())) {
					maximumConnect++;
					// Check if Player won vertically.
					if (maximumConnect >= MINIMUM_CHAIN_TO_WIN) {
						currentPlayer.wins();
						System.out.println("\nPlayer " + currentPlayer.id() + " won vertically!");
						isGameOver = true;
						break outerLoop;
					}
				} else {
					maximumConnect = 1;
				}
			}
			maximumConnect = 1;
		}
		return isGameOver;
	}
    
	/**
	 * Scanning in lines from top-left to bottom-right, check board for diagonal
	 * connects. Start checking at bottom-left and stop checking at top-left.
	 * 
	 * @return
	 */
	private boolean diagonalScanDownRight1() {
		int row, column, maximumConnect, startPoint;
		boolean isGameOver = false;

		outerLoop: for (startPoint = NUMBER_OF_ROWS - 2, maximumConnect = 1; startPoint >= 0; startPoint--) {
			for (row = startPoint, column = 0; row < NUMBER_OF_ROWS - 1 && column < NUMBER_OF_COLUMNS - 1; row++, column++) {
				if (gameBoard[row][column].equals(currentPlayer.disc()) && gameBoard[row + 1][column + 1].equals(currentPlayer.disc())) {
					maximumConnect++;
					// Check if Player won diagonally.
					if (maximumConnect >= MINIMUM_CHAIN_TO_WIN) {
						currentPlayer.wins();
						System.out.println("\nPlayer " + currentPlayer.id() + " won diagonally!");
						isGameOver = true;
						break outerLoop;
					}
				} else {
					maximumConnect = 1;
				}
			}
			maximumConnect = 1;
		}
		return isGameOver;
	}
    
	/**
	 * Scanning in lines from top-left to bottom-right, checks board for diagonal
	 * connects. Start checking at top-left and stop checking at top-right.
	 * 
	 * @return
	 */
	private boolean diagonalScanDownRight2() {
		int row, column, maximumConnect, startPoint;
		boolean isGameOver = false;

		outerLoop: for (startPoint = 1, maximumConnect = 1; startPoint < NUMBER_OF_COLUMNS - 1; startPoint++) {
			for (row = 0, column = startPoint; row < NUMBER_OF_ROWS - 1 && column < NUMBER_OF_COLUMNS - 1; row++, column++) {
				if (gameBoard[row][column].equals(currentPlayer.disc()) && gameBoard[row + 1][column + 1].equals(currentPlayer.disc())) {
					maximumConnect++;
					// Check if Player won diagonally.
					if (maximumConnect >= MINIMUM_CHAIN_TO_WIN) {
						currentPlayer.wins();
						System.out.println("\nPlayer " + currentPlayer.id() + " won diagonally!");
						isGameOver = true;
						break outerLoop;
					}
				} else {
					maximumConnect = 1;
				}
			}
			maximumConnect = 1;
		}
		return isGameOver;
	}

	/**
	 * Scanning in lines from top-right to bottom-left, checks board for diagonal
	 * connects. Start checking at bottom-right and stop checking at top-right.
	 * 
	 * @return
	 */
	private boolean diagonalScanDownLeft1() {
		int row, column, maximumConnect, startPoint;
		boolean isGameOver = false;

		outerLoop: for (startPoint = NUMBER_OF_ROWS - 2, maximumConnect = 1; startPoint >= 0; startPoint--) {
			for (row = startPoint, column = NUMBER_OF_COLUMNS - 1; row < NUMBER_OF_ROWS - 1 && column > 0; row++, column--) {
				if (gameBoard[row][column].equals(currentPlayer.disc()) && gameBoard[row + 1][column - 1].equals(currentPlayer.disc())) {
					maximumConnect++;
					// Check if Player won diagonally.
					if (maximumConnect >= MINIMUM_CHAIN_TO_WIN) {
						currentPlayer.wins();
						System.out.println("\nPlayer " + currentPlayer.id() + " won diagonally!");
						isGameOver = true;
						break outerLoop;
					}
				} else {
					maximumConnect = 1;
				}
			}
			maximumConnect = 1;
		}
		return isGameOver;
	}

	/**
	 * Scanning in lines from top-right to bottom-left, check board for diagonal
	 * connects. Start checking at top-right and stop checking at top-left.
	 * 
	 * @return
	 */
	private boolean diagonalScanDownLeft2() {
		int row, column, maximumConnect, startPoint;
		boolean isGameOver = false;

		outerLoop: for (startPoint = NUMBER_OF_COLUMNS - 2, maximumConnect = 1; startPoint > 0; startPoint--) {
			for (row = 0, column = startPoint; row < NUMBER_OF_ROWS - 1 && column > 0; row++, column--) {
				if (gameBoard[row][column].equals(currentPlayer.disc()) && gameBoard[row + 1][column - 1].equals(currentPlayer.disc())) {
					maximumConnect++;
					// Check if Player won diagonally.
					if (maximumConnect >= MINIMUM_CHAIN_TO_WIN) {
						currentPlayer.wins();
						System.out.println("\nPlayer " + currentPlayer.id() + " won diagonally!");
						isGameOver = true;
						break outerLoop;
					}
				} else {
					maximumConnect = 1;
				}
			}
			maximumConnect = 1;
		}
		return isGameOver;
	}
}
