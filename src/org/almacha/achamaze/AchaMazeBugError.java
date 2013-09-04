package org.almacha.achamaze;

/**
 * This exception is for cases that cannot happen
 * unless there is a bug in one of the AchaMaze classes,
 * or some other strange situation (RAM problem, etc.).
 * @author Raphael Champeimont
 *
 */
public class AchaMazeBugError extends AchaMazeError {

	public AchaMazeBugError() {
		super();
	}

	public AchaMazeBugError(String message) {
		super(message);
	}

}