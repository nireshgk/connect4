package game;

public class ConnectFour {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//Introduction text.
        System.out.println(
                  "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n"
                + "Connect Four is a two-player connection game in which the players first choose\n"
                + "a colour and then take turns dropping colored discs from the top into\n"
                + "a seven-column, six-row grid. The disc fall straight down, occupying the next \n"
                + "available space within the column.The objective of the game is to connect \n"
                + "three of one's own discs of the same color next to each other \n"
                + "vertically, horizontally, or diagonally before your opponent.\n"
                + "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n"
        		); 

		GameBoard connectFour = new GameBoard();
		connectFour.startGame();
		connectFour.playGame();
		connectFour.endGame();
	}
}
