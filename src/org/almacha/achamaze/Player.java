package org.almacha.achamaze;

/** 
 * This class represents a player that is in the maze.
 * You cannot create directly an object of this class, instead you have to use the
 * addPlayer() method from the Maze class.
 * @author River Champeimont
 *
 */
public class Player {
	private int playerId;
	private int positionLine;
	private int positionColumn;
	private Maze maze;
	private boolean inMaze = true;
	private PlayerStateForMazeAlgo playerStateForMazeAlgo;
	private MazeSolvingAlgorithm mazeSolvingAlgorithm;
	
	public Player(Maze maze, int i) {
		this.maze = maze;
		playerId = i;
		positionLine = maze.getStartLine();
		positionColumn = maze.getStartColumn();
	}

	/**
	 * Returns the current position of the player.
	 * @return column
	 */
	public int getPositionColumn() throws PlayerNotInMazeException {
		checkIsInMaze();
		return positionColumn;
	}

	/**
	 * Returns the current position of the player.
	 * @return line
	 */
	public int getPositionLine() throws PlayerNotInMazeException {
		checkIsInMaze();
		return positionLine;
	}

	/**
	 * Sets the current position of the player.
	 * This puts the player back in the maze,
	 * so isInMaze() will then return true.
	 * @param i line
	 * @param j column
	 * @throws CellDoesNotExistException in case (i,j) does not exist
	 */
	public void setPosition(int i, int j) throws CellDoesNotExistException {
		maze.checkCellExists(i, j);
		this.positionLine = i;
		this.positionColumn = j;
	}
	
	/**
	 * Get player out of the maze.
	 * Method isInMaze() will then return false.
	 */
	public void exitMaze() {
		positionLine = -1;
		positionColumn = -1;
		inMaze = false;
	}
	
	
	/**
	 * Tells whether the player can move in direction d.
	 * It always returns false if player is not in maze.
	 * @param d direction where we want to know if we can move
	 * @return whether it is possible
	 */
	public boolean canMove(Direction d) {
		if (inMaze) {
			try {
				return maze.canMove(positionLine, positionColumn, d);
			} catch (CellDoesNotExistException e) {
				// This is impossible, because the current player is necessarly in the maze.
				throw new AchaMazeBugError("Impossible CellDoesNotExistException raised in Player.canMove(): " + e.getMessage());
			}
		} else {
			// Not in maze, so cannot move from one cell to another.
			return false;
		}
	}
	
	/** Move in the requested direction.
	 * @param direction
	 * @throws PlayerCannotMoveException in case we cannot move in this direction
	 */
	public void move(Direction direction) throws PlayerCannotMoveException {
		if (canMove(direction)) {
			switch (direction) {
			case NORTH:
				positionLine--;
				break;
			case SOUTH:
				positionLine++;
				break;
			case WEST:
				positionColumn--;
				break;
			case EAST:
				positionColumn++;
				break;
			}
			if (!maze.cellExists(positionLine, positionColumn)) {
				exitMaze();
			}
		} else {
			throw new PlayerCannotMoveException("Player "
					+ this
					+ " with id "
					+ playerId
					+ " cannot move from ("
					+ positionLine
					+ ","
					+ positionColumn
					+ ") to "
					+ direction.toString());
		}
	}

	/**
	 * Tells whether the player is in the maze.
	 * @return whether or not player is in the maze
	 */
	public boolean isInMaze() {
		return inMaze;
	}
	
	/**
	 * Throws PlayerNotInMazeException if player is not in maze.
	 * @throws PlayerNotInMazeException in case player is not in maze
	 */
	public void checkIsInMaze() throws PlayerNotInMazeException {
		if (!isInMaze()) {
			throw new PlayerNotInMazeException();
		}
	}

	/** Get player id.
	 * @return player id
	 */
	public int getPlayerId() {
		return playerId;
	}
	
	public PlayerStateForMazeAlgo getStateForMazeAlgo() {
		if (playerStateForMazeAlgo == null) {
			playerStateForMazeAlgo = new PlayerStateForMazeAlgo(maze, this);
		}
		return playerStateForMazeAlgo;
	}

	/**
	 * Returns the solving algorithm associated with the player.
	 * It returns null if there is none.
	 * @return the algorithm
	 */
	public MazeSolvingAlgorithm getMazeSolvingAlgorithm() {
		return mazeSolvingAlgorithm;
	}

	/**
	 * Associates a maze solving algorithm to that player.
	 * This is just for convenience, to store the algorithm
	 * reference with the player. This method will check that
	 * the given algorithm is associated with this player state.
	 * @param mazeSolvingAlgorithm a instance of maze solving algorithm
	 * @throws AchaMazeException in case the algorithm is associated to another player
	 */
	public void setMazeSolvingAlgorithm(MazeSolvingAlgorithm mazeSolvingAlgorithm) throws AchaMazeException {
		if (mazeSolvingAlgorithm.getPlayerState().getPlayer() != this) {
			throw new AchaMazeException("The given algorithm is associated to another player.");
		}
		this.mazeSolvingAlgorithm = mazeSolvingAlgorithm;
	}
	
}