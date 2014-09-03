package mcBlueprint;

import java.awt.Color;

public class Grid {
	
	private byte grid[][];
	Window window;
	Color colors[] = {new Color(0, 0, 0), new Color(100, 100, 100), new Color(255, 255, 255), new Color(255, 0, 0), new Color(0, 150, 0), new Color(0, 0, 255), new Color(255, 250, 0), new Color(255, 0, 255), new Color(0, 150, 255)};
	String name;
	private int count;
	
	public Grid(Window win, String n) {
		window = win;
		name = n;
		count = 0;
		
		grid = new byte[500][500];
		
		for (int i = 0; i < 500; i++) {
			for (int j = 0; j < 500; j++) {
				grid[i][j] = 0;
			}
		}
	}
	
	public Grid(byte[][] g, Window win, String n, int c) {
		window = win;
		name = n;
		count = c;
		
		grid = g;
	}

	public byte[][] getGrid() {
		return grid;
	}
	
	public void fillBlock(int x, int y, byte color) {
		try {
			if (grid[x][y] == 0) {
				grid[x][y] = color;
				count++;
			} else {
				grid[x][y] = color;
			}
		} catch (Exception e) {
			
		}
	}
	
	public void clearBlock(int x, int y) {
		try {
			if (grid[x][y] > 0) {
				grid[x][y] = 0;
				count--;
			}
		} catch (Exception e) {
			
		}
	}

	public void setGridTo(byte[][] gridread, String n) {
		grid = gridread;
		name = n;
	}

	public Color getColor(byte color) {
		return colors[color];
	}

	public String name() {
		return name;
	}
	
	public void resetCount() {
		count = 0;
	}
	
	public int count() {
		return count;
	}
	
}
