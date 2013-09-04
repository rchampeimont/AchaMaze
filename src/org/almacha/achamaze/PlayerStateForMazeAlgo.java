package org.almacha.achamaze;

/**
 * An instance of this class is given to
 * a maze solving algorithm so that it knows
 * the player situation.
 * @author almacha
 *
 */
public class PlayerStateForMazeAlgo {
	private Maze maze;
	private Player player;
	
	public PlayerStateForMazeAlgo(Maze maze, Player player) {
		this.maze = maze;
		this.player = player;
	}
	
	public boolean canMove(Direction direction) {
		return player.canMove(direction);
	}
	
	public void move(Direction direction) throws PlayerCannotMoveException {
		player.move(direction);
	}

	public Maze getMaze() {
		return maze;
	}

	public Player getPlayer() {
		return player;
	}
}
