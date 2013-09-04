package org.almacha.achamaze;

/**
 * Maze examples.
 * @author Raphael Champeimont
 *
 */
public class MazeExamples {
	public static Maze ExampleMaze1() {
		try {
			Maze maze = new Maze(4, 5);
			
			// Filled cells
			maze.setFilled(1, 3, true);
			
			// North wall
			for (int j = 0; j < maze.getWidth(); j++) {
				maze.setWallIsUp(0, j, Direction.NORTH, true);
			}
			
			// South wall
			for (int j = 0; j < maze.getWidth(); j++) {
				maze.setWallIsUp(maze.getHeight()-1, j, Direction.SOUTH, true);
			}
			
			// West wall
			for (int i = 0; i < maze.getHeight(); i++) {
				maze.setWallIsUp(i, 0, Direction.WEST, true);
			}
			
			// Est wall
			for (int i = 0; i < maze.getHeight(); i++) {
				maze.setWallIsUp(i, maze.getWidth()-1, Direction.EAST, true);
			}
			
			// Make a hole for the ext
			maze.setWallIsUp(2, 4, Direction.EAST, false);
			
			// Inner walls
			maze.setWallIsUp(0, 0, Direction.EAST, true);
			maze.setWallIsUp(1, 0, Direction.EAST, true);
			maze.setWallIsUp(2, 0, Direction.EAST, true);
			maze.setWallIsUp(2, 1, Direction.EAST, true);
			maze.setWallIsUp(3, 1, Direction.EAST, true);
			maze.setWallIsUp(2, 2, Direction.EAST, true);
			maze.setWallIsUp(2, 3, Direction.SOUTH, true);
			
			return maze;
		} catch (AchaMazeException e) {
			throw new AchaMazeBugError("Bug in maze example 1: " + e.getMessage());
		}
	}
}
