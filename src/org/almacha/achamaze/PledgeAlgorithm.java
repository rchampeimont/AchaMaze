package org.almacha.achamaze;

/**
 * An implementation of the Pledge Maze Solving Algorithm.
 * See http://en.wikipedia.org/wiki/Maze_solving_algorithm#Pledge_algorithm
 * @author almacha
 *
 */
public class PledgeAlgorithm extends MazeSolvingAlgorithm {
	private boolean followingWall = false;
	private int rotation = 0;
	
	// Default direction (any works).
	private Direction going = Direction.NORTH;

	public PledgeAlgorithm(PlayerStateForMazeAlgo playerState) {
		super(playerState);
	}
	
	private void right() {
		going = going.right();
		rotation++;
	}
	
	private void left() {
		going = going.left();
		rotation--;
	}
	
	protected Direction decide() {
		if (followingWall && rotation == 0) {
			// "When the solver is facing the original direction again,
			// and the angular sum of the turns made is 0,
			// the solver leaves the obstacle and continues
			// moving in its original direction." (Wikipedia)
			followingWall = false;
		}
		
		if (!followingWall) {
			// We have not yet found a wall.
			// Can we go forward?
			if (getPlayerState().canMove(going)) {
				// We can go forward, so let's do it.
				return going;
			} else {
				// We can't go forward, so start following the wall.
				followingWall = true;
				// We want to have the wall on the left, so we turn right.
				right();
			}
		}
		
		
		// We are following a wall.
		// Can we go left?
		if (getPlayerState().canMove(going.left())) {
			// We can go left. So we turn to the left.
			left();
			// And go forward.
			return going;
		} else {
			// Turn right until we can go forward.
			int i = 0;
			while (!getPlayerState().canMove(going)) {
				right();
				i++;
				if (i >= 4) {
					// We cannot move in any way,
					// so let's commit suicide by crashing into a wall.
					// (Technichally, we go in a random impossible direction,
					// which will raise a PlayerCannotMoveException.)
					return going;
				}
			}
			// Go forward.
			return going;
		}
	}
	
}
