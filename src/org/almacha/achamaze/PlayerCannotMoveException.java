package org.almacha.achamaze;

/**
 * An exception raised when you request to move a player and it is not possible.
 * @author almacha
 *
 */
public class PlayerCannotMoveException extends AchaMazeException {

	public PlayerCannotMoveException() {
		super();
	}

	public PlayerCannotMoveException(String message) {
		super(message);
	}

}