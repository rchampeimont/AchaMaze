package org.almacha.achamaze;

import java.util.LinkedList;

/** 
 * This class represents a maze.
 * @author Raphael Champeimont
 */
public class Maze {
	private Cell[][] maze;
	private int height;
	private int width;
	private LinkedList<Player> players;
	private int nextPlayerId = 0;
	private int startLine = 0;
	private int startColumn = 0;
	
	/** 
	 * Returns whether the cell (i,j) exists in the maze.
	 * This function does just check whether this cell is in the range.
	 * @param i the line number of the cell
	 * @param j the column number of the cell
	 * @return a boolean which tells you whether the cell exists
	 */
	public boolean cellExists(int i, int j) {
		return (i >= 0 && j >= 0 && i < height && j < width);
	}
	
	/** 
	 * Raises a CellDoesNotExistException if and only if cellExists(i, j) is false.
	 * @param i the line number of the cell
	 * @param j the column number of the cell
	 * @throws CellDoesNotExistException in case the cell does not exist
	 */
	public void checkCellExists(int i, int j) throws CellDoesNotExistException {
		if (!cellExists(i, j)) {
			throw new CellDoesNotExistException("Cell (" + i + "," + j + ") does not exist");
		}
	}
	
	private Cell getCell(int i, int j) throws CellDoesNotExistException {
		// Calling checkCellExists() is usefull because of the last row and last column in this.maze that do not really exist. 
		checkCellExists(i, j);
		return maze[i][j];
	}
	
	

	/**
	 * This constructor creates a new maze.
	 * @param height the number of lines, must be >= 1
	 * @param width the number of columns, must be >= 1
	 * @throws AchaMazeException thrown if width or height is invalid
	 */
	public Maze(int height, int width) throws AchaMazeException {
		if (height < 1 || width < 1) {
			throw new AchaMazeException("Height and width must be >= 1");
		}
		this.height = height;
		this.width = width;
		this.maze = new Cell[height+1][width+1];
		for (int i = 0; i < height+1; i++) {
			for (int j = 0; j < width+1; j++) {
				maze[i][j] = new Cell();
			}
		}
		
		resetPlayers();
	}
	
	/** 
	 * This method sets whether a wall is there or not.
	 * @param i the line of the cell whose wall you want to modify
	 * @param j the column of the cell whose wall you want to modify
	 * @param wall the wall of the cell you want to modify (North, Est, South or West)
	 * @param isUp whether you want to enable or disable this wall
	 */
	public void setWallIsUp(int i, int j, Direction wall, boolean isUp) throws CellDoesNotExistException {
		checkCellExists(i, j);
		switch (wall) {
		case NORTH:
			maze[i][j].getNorthWall().setIsUp(isUp);
			break;
		case WEST:
			maze[i][j].getWestWall().setIsUp(isUp);
			break;
		case SOUTH:
			maze[i+1][j].getNorthWall().setIsUp(isUp);
			break;
		case EAST:
			maze[i][j+1].getWestWall().setIsUp(isUp);
			break;
		}
	}
	
	/** 
	 * This method returns whether a wall is up or not.
	 * @param i the line of the cell whose wall you want to know if it is up
	 * @param j the column of the cell whose wall you want to know if it is up
	 * @param wall the wall of the cell you want to look at (North, Est, South or West)
	 * @return a boolean telling you whether the wall is there or not
	 * @throws CellDoesNotExistException exception thrown if the required cell does not exist
	 */
	public boolean isWallUp(int i, int j, Direction wall) throws CellDoesNotExistException {
		checkCellExists(i, j);
		boolean r = false;
		switch (wall) {
		case NORTH:
			r = maze[i][j].getNorthWall().getIsUp();
			break;
		case WEST:
			r = maze[i][j].getWestWall().getIsUp();
			break;
		case SOUTH:
			r = maze[i+1][j].getNorthWall().getIsUp();
			break;
		case EAST:
			r = maze[i][j+1].getWestWall().getIsUp();
			break;
		}
		return r;
	}
	
	/**
	 * Returns the width (number of columns) of the maze.
	 * @return width of maze
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns the height (number of lines) of the maze.
	 * @return height of maze
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Sets whether a cell is filled or not. A filled cell prevents a player from going on that cell.
	 * @param i the line of the cell
	 * @param j the column of the cell
	 * @param filled wheter the cell should be filled
	 * (player cannot go on it) or empty (player can go on it)
	 * @throws CellDoesNotExistException
	 */
	public void setFilled(int i, int j, boolean filled) throws CellDoesNotExistException {
		getCell(i, j).setFilled(filled);
	}
	
	/**
	 * Tells whether the cell (i,j) is filled.
	 * @param i the line of the cell
	 * @param j the column of the cell
	 * @return a boolean telling whether the cell is filled or not
	 * @throws CellDoesNotExistException in case (i,j) is not a cell in the maze
	 */
	public boolean isFilled(int i, int j) throws CellDoesNotExistException {
		return getCell(i, j).getFilled();
	}
	

	/** 
	 * Adds a player in the maze.
	 */
	public void addPlayer() {
		players.add(new Player(this, nextPlayerId));
		nextPlayerId++;
	}

	/**
	 * Deletes all player in the maze.
	 */
	public void resetPlayers() {
		nextPlayerId = 0;
		this.players = new LinkedList<Player>();
	}
	
	/**
	 * This method returns the number of players in the maze.
	 * @return the number of players
	 */
	public int getNumberOfPlayers() {
		return players.size();
	}
	
	public Player getPlayer(int playerId) {
		return players.get(playerId);
	}
	
	/**
	 * Defines the position at which new players start in the maze.
	 * @param i line
	 * @param j column
	 */
	public void setStartPosition(int i, int j) throws CellDoesNotExistException {
		checkCellExists(i, j);
		startLine = i;
		startColumn = j;
	}
	
	/**
	 * Returns the current start position (line).
	 * @return line
	 */
	public int getStartLine() {
		return startLine;
	}
	
	/**
	 * Returns the current start position (column).
	 * @return column
	 */
	public int getStartColumn() {
		return startColumn;
	}
	
	
	/**
	 * Tells whether it is possible to move in direction d from cell (i, j).
	 * @param i line of the cell
	 * @param j column of the cell
	 * @param d direction where we want to go
	 * @return whether we can move
	 * @throws CellDoesNotExistException in case (i,j) is not a cell in the maze
	 */
	public boolean canMove(int i, int j, Direction d) throws CellDoesNotExistException {
		checkCellExists(i, j);
		if (isWallUp(i, j, d)) {
			// There is a wall that is blocking us, so we cannot move that way.
			return false;
		} else {
			// No wall, so let's see if we can move that way.
			int i1 = i;
			int j1 = j;
			switch (d) {
			case NORTH:
				i1--;
				break;
			case SOUTH:
				i1++;
				break;
			case WEST:
				j1--;
				break;
			case EAST:
				j1++;
				break;
			}
			// Cell (i1,j1) is the new position. Is it in the maze?
			if (cellExists(i1, j1)) {
				// New position is in the maze. Is it filled?
				if (isFilled(i1, j1)) {
					// Yes, so we cannot move.
					return false;
				} else {
					// No, we can move.
					return true;
				}
			} else {
				// No, the new position is out of the maze. We can then move (an exit!).
				return true;
			}
		}
	}
}