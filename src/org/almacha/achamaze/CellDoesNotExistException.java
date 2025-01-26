package org.almacha.achamaze;

/**
 * An exception raised when a reference is made to a cell that does not exist.
 * @author River Champeimont
 *
 */
public class CellDoesNotExistException extends AchaMazeException {

	public CellDoesNotExistException() {
		super();
	}

	public CellDoesNotExistException(String message) {
		super(message);
	}

}