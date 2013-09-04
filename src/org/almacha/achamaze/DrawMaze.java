package org.almacha.achamaze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

/**
 * This class enables you to display a maze (class Maze) on the screen.
 * @author Raphael Champeimont
 *
 */
public class DrawMaze {
	private Color mazeBackgroundColor = Color.white;
	private Color mazeWallColor = Color.black;
	private Color mazeFilledCellColor = Color.black;
	private Maze maze;
	
	/**
	 * Constructor for DrawMaze.
	 * @param maze The maze to draw.
	 */
	public DrawMaze(Maze maze) {
		this.maze = maze;
	}
	
	/**
	 * This method draws the maze on g, using a surface of size displayAreaSize.
	 * @param g the Graphics object to use to draw the maze
	 * @param displayAreaSize the size of the rectangle on which the maze will be drawn
	 */
	public void draw(Graphics g, Dimension displayAreaSize) {
		if (maze != null) {
			int mazeWidthInNumberOfCells = maze.getWidth();
			int mazeHeightInNumberOfCells = maze.getHeight();
			int displayWidthInNumberOfCells = mazeWidthInNumberOfCells + 2;
			int displayHeightInNumberOfCells = mazeHeightInNumberOfCells + 2;
			int displayCellWidth = displayAreaSize.width / displayWidthInNumberOfCells;
			int displayCellHeight = displayAreaSize.height / displayHeightInNumberOfCells;
			
			g.setColor(mazeBackgroundColor);
			g.fillRect(0, 0, displayAreaSize.width, displayAreaSize.height);
			
			try {
				for (int i = 0; i < mazeHeightInNumberOfCells; i++) {
					for (int j = 0; j < mazeWidthInNumberOfCells; j++) {
						g.setColor(mazeFilledCellColor);
						if (maze.isFilled(i, j)) {
							g.fillRect((j+1)*displayCellWidth, (i+1)*displayCellHeight, displayCellWidth, displayCellHeight);
						}
						g.setColor(mazeWallColor);
						if (maze.isWallUp(i, j, Direction.NORTH)) {
							g.drawLine((j+1)*displayCellWidth, (i+1)*displayCellHeight, (j+2)*displayCellWidth, (i+1)*displayCellHeight);
						}
						if (maze.isWallUp(i, j, Direction.WEST)) {
							g.drawLine((j+1)*displayCellWidth, (i+1)*displayCellHeight, (j+1)*displayCellWidth, (i+2)*displayCellHeight);
						}
					}
				}
			
				// draw the south line
				g.setColor(mazeWallColor);
				for (int j = 0; j < mazeWidthInNumberOfCells; j++) {
					if (maze.isWallUp(mazeHeightInNumberOfCells - 1, j, Direction.SOUTH)) {
						g.drawLine((j+1)*displayCellWidth, (mazeHeightInNumberOfCells+1)*displayCellHeight, (j+2)*displayCellWidth, (mazeHeightInNumberOfCells+1)*displayCellHeight);
					}
				}
				
				// draw the est line
				g.setColor(mazeWallColor);
				for (int i = 0; i < mazeHeightInNumberOfCells; i++) {
					if (maze.isWallUp(i, mazeWidthInNumberOfCells - 1, Direction.EAST)) {
						g.drawLine((mazeWidthInNumberOfCells+1)*displayCellWidth, (i+1)*displayCellHeight, (mazeWidthInNumberOfCells+1)*displayCellWidth, (i+2)*displayCellHeight);
					}
				}
				
			} catch (CellDoesNotExistException e) {
				System.out.println("Warning: A CellDoesNotExistException was raised.\n" + e.getMessage());
			}
			
			// Draw players
			for (int playerId=0; playerId<maze.getNumberOfPlayers(); playerId++) {
				try {
					Player player = maze.getPlayer(playerId);
					int i = player.getPositionLine();
					int j = player.getPositionColumn();
					g.setColor(getColorOfPlayer(playerId));
					int x0 = (j+1)*displayCellWidth;
					int y0 = (i+1)*displayCellHeight;
					int w = displayCellWidth;
					int h = displayCellHeight;
					int[] X = {x0+w/2, x0+w, x0+w/2, x0};
					int[] Y = {y0, y0+h/2, y0+h, y0+h/2};
					g.fillPolygon(X, Y, 4);
				} catch (PlayerNotInMazeException e) {
					// Do nothing, there is nothing to draw for a player not in the maze.
				}
			}
			
		}
	}

	public Color getMazeBackgroundColor() {
		return mazeBackgroundColor;
	}

	public void setMazeBackgroundColor(Color mazeBackgroundColor) {
		this.mazeBackgroundColor = mazeBackgroundColor;
	}

	public Color getMazeFilledCellColor() {
		return mazeFilledCellColor;
	}

	public void setMazeFilledCellColor(Color mazeFilledCellColor) {
		this.mazeFilledCellColor = mazeFilledCellColor;
	}

	public Color getMazeWallColor() {
		return mazeWallColor;
	}

	public void setMazeWallColor(Color mazeWallColor) {
		this.mazeWallColor = mazeWallColor;
	}
	
	/**
	 * This method returns the color of the n-th player.
	 * If player n does not exist, the returned color is unspecified.
	 * @return color of n-th player
	 */
	public Color getColorOfPlayer(int n) {
		return new Color(Color.HSBtoRGB(((float) n)/((float) maze.getNumberOfPlayers()), 1.0f, 1.0f));
	}
}
