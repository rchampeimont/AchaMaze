package org.almacha.achamaze;

/**
 * Represents a direction (North, East, South, West).
 * @author Raphael Champeimont
 *
 */
public enum Direction {
	NORTH,
	EAST,
	SOUTH,
	WEST;
	
	/**
	 * Returns the direciton on the left of the given one.
	 * @return the computed direction
	 */
	public Direction left() {
		switch (this) {
		case NORTH:
			return WEST;
		case EAST:
			return NORTH;
		case SOUTH:
			return EAST;
		default: // WEST
			return SOUTH;
		}
	}
	
	/**
	 * Returns the direciton on the right of the given one.
	 * @return the computed direction
	 */
	public Direction right() {
		switch (this) {
		case NORTH:
			return EAST;
		case EAST:
			return SOUTH;
		case SOUTH:
			return WEST;
		default: // WEST
			return NORTH;
		}
	}
}
