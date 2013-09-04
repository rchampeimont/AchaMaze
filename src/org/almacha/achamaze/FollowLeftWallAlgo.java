package org.almacha.achamaze;

/**
 * An implementation of the "follow left wall" maze solving algorithm.
 * This algorithm may not enable you to escape from the maze.
 * This algorithm works by first going in a random direction
 * until we find a wall. Then we follow that wall forever
 * and hope (but that may not happen) we escape form the maze.
 * @author almacha
 *
 */
public class FollowLeftWallAlgo extends MazeSolvingAlgorithm {
	private boolean followingWall = false;
	
	// Default direction (any works).
	private Direction going = Direction.NORTH;

	public FollowLeftWallAlgo(PlayerStateForMazeAlgo playerState) {
		super(playerState);
	}
	
	protected Direction decide() {
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
				going = going.right();
			}
		}
		
		
		// We are following a wall.
		// Can we go left?
		if (getPlayerState().canMove(going.left())) {
			// We can go left. So we turn to the left.
			going = going.left();
			// And go forward.
			return going;
		} else {
			// Turn right until we can go forward.
			int i = 0;
			while (!getPlayerState().canMove(going)) {
				going = going.right();
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
