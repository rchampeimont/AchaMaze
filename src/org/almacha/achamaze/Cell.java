package org.almacha.achamaze;
	
class Cell {
	private Wall northWall;
	private Wall westWall;
	private boolean filled = false;
	
	public Cell() {
		this.northWall = new Wall();
		this.westWall = new Wall();
	}
	
	public Wall getNorthWall() {
		return northWall;
	}
	
	public Wall getWestWall() {
		return westWall;
	}
	
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
	
	public boolean getFilled() {
		return this.filled;
	}
}
