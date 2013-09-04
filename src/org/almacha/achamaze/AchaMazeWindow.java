package org.almacha.achamaze;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

/**
 * The main program window.
 * @author almacha
 *
 */
class AchaMazeWindow extends JFrame {
	private Maze maze;
	private DrawMaze drawMaze;
	private String whatWeSay = "";
	
	private class MyPanel extends JPanel {
		public MyPanel() {
		}
		
		private void paintMaze(Graphics g) {
			if (drawMaze != null) {
				drawMaze.draw(g, getSize());
			}
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			paintMaze(g);
			g.setColor(Color.black);
			g.drawString(whatWeSay, 5, 15);
		}
	}
	
	public void say(String s) {
		whatWeSay = s;
		System.out.println(s);
		repaint();
	}
	
	public AchaMazeWindow() throws AchaMazeException {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 480);
		setTitle("AchaMaze");
		
		MyPanel panel = new MyPanel();
		add(panel);
		setVisible(true);
		
		say("Welcome.");
		
		maze = MazeExamples.ExampleMaze1();
		drawMaze = new DrawMaze(maze);
		panel.repaint();
		
		// Players
		maze.addPlayer();
		// From this position, both FollowLeftWall and Pledge work.
		maze.getPlayer(maze.getNumberOfPlayers()-1).setPosition(0, 0);
		
		maze.addPlayer();
		// From that position, Pledge works but FollowLeftWall loops forever.
		maze.getPlayer(maze.getNumberOfPlayers()-1).setPosition(3, 3);
		
		
		panel.repaint();
		
		// Load solving algorithms
		int n = maze.getNumberOfPlayers();
		for (int playerId = 0; playerId < n; playerId++) {
			Player player = maze.getPlayer(playerId);
			MazeSolvingAlgorithm algo = new FollowLeftWallAlgo(player.getStateForMazeAlgo());
			player.setMazeSolvingAlgorithm(algo);
			
			// Add player at same place but with other algorithm
			maze.addPlayer();
			Player player2 = maze.getPlayer(maze.getNumberOfPlayers()-1);
			player2.setPosition(player.getPositionLine(), player.getPositionColumn());
			MazeSolvingAlgorithm algo2 = new PledgeAlgorithm(player2.getStateForMazeAlgo());
			player2.setMazeSolvingAlgorithm(algo2);
		}
		
		int numberOfPlayersInMaze = maze.getNumberOfPlayers();
		while (numberOfPlayersInMaze > 0) {
			// Wait so the user can see the changes.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				throw new AchaMazeException("InterruptedException thrown: " + e);
			}
			
			for (int playerId = 0; playerId < maze.getNumberOfPlayers(); playerId++) {
				Player player = maze.getPlayer(playerId);
				if (!player.isInMaze()) {
					// Player out of maze, so skip.
					continue;
				}
				
				// Player is in maze.
				player.getMazeSolvingAlgorithm().move();
				
				// Is player still in maze?
				if (!player.isInMaze()) {
					numberOfPlayersInMaze--;
					say("Player "
							+ player
							+ " with id "
							+ player.getPlayerId()
							+ " exited from the maze.");
				}
				
				// Display the new game state.
				panel.repaint();

			}
			
			
			
		}
		
		say("All players have exited maze.");
		
	}
	
}
