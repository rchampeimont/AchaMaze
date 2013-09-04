package org.almacha.achamaze;

/**
 * An exception raised when a method which
 * is only valid for a player in the maze is called
 * on a player that is not in the maze.
 * @author Raphael Champeimont
 *
 */
public class PlayerNotInMazeException extends AchaMazeException {

	public PlayerNotInMazeException() {
		super();
	}

	public PlayerNotInMazeException(String message) {
		super(message);
	}

}