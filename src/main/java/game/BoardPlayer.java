package game;

/**
 * Board player attributes
 */
public class BoardPlayer {

	/**
	 * Player ID
	 */
	private int id;

	/**
	 * Player Disc
	 */
	private String disc = null;

	/**
	 * Player Disc Name
	 */
	private String discName = null;

	/**
	 * Player winning status
	 */
	private boolean win = false;

	/**
	 * Constructor to initialize player
	 * 
	 * @param id
	 * @param disc
	 * @param discName
	 */
	public BoardPlayer(int id, String disc, String discName) {
		setId(id);
		setDisc(disc);
		setDiscName(discName);
	}
	
	/**
	 * @return Player ID
	 */
	public int id() {
		return id;
	}

	/**
	 * Player ID
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Player Disc
	 */
	public String disc() {
		return disc;
	}

	/**
	 * Player Disc
	 * 
	 * @param disc
	 */
	public void setDisc(String disc) {
		this.disc = disc;
	}

	/**
	 * @return Player Disc Name
	 */
	public String discName() {
		return discName;
	}

	/**
	 * Player Disc Name
	 * 
	 * @param discName
	 */
	public void setDiscName(String discName) {
		this.discName = discName;
	}

	/**
	 * @return player winning status
	 */
	public boolean isWin() {
		return win;
	}

	/**
	 * set player winning status
	 * 
	 * @param win
	 */
	public void setWin(boolean win) {
		this.win = win;
	}

	/**
	 * set player as won
	 */
	public void wins() {
		setWin(true);
	}
}
