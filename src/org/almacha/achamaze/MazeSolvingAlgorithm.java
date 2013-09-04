package org.almacha.achamaze;

/**
 * A class from which derive the classes that implement maze solving algorithm.
 * @author almacha
 *
 */
public abstract class MazeSolvingAlgorithm {
	private PlayerStateForMazeAlgo playerState;
	
	/**
	 * This method decides in which direction the player should go.
	 * When this method is called, the player will then move in the
	 * chosen direction. So your implementation of decide() can
	 * assume that the player is moved in the direction you return.
	 * This method should never be called directly,
	 * even from derived classes, use move() instead.
	 * @return the chosen direction
	 */
	protected abstract Direction decide();
	
	/**
	 * This method calls decide() to decide in which
	 * direction to go. Then it moves in that direction.
	 */
	public final void move() throws PlayerCannotMoveException {
		Direction decision = decide();
		playerState.move(decision);
	}
	
	public MazeSolvingAlgorithm(PlayerStateForMazeAlgo playerState) {
		this.playerState = playerState;
	}

	public PlayerStateForMazeAlgo getPlayerState() {
		return playerState;
	}
	

}
