package mazeAI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class MazeGenerator {
	
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public static final int WALL = 1;
	public static final int NO_WALL = 0; 
	public static final int NOT_EXIST = -1; 
	
	public static final int CELL_WIDTH = 20;
	public static int marginHorizental = 50;

	public int height, width;
	public int N;																//number of cells
	public Cell cells[];
	
	
	/**
	 * Constructor
	 */
	public MazeGenerator(int width, int height){
		
		this.height = height;
		this.width = width;
		
		N = height * width;
		cells = new Cell[N]; 													// Array of cells 
		
		initCells();
		killAndHunt();
		//printCells();
	}
	
	public Cell[] getCells() {
		return cells;
	}
	
	
	private void initCells() {
		for (int i = 0; i < N; i++ ) {
			int row = i / width;											//number of row
			int column = i % width;											//number of column	
			cells[i] = new Cell(row, column);
		}
	}
	
	/*
	 * this method chooses random cell as the start for the random walk (kill)
	 */
	public void killAndHunt() {
		Random random = new Random();
		int currentIndex = random.nextInt(N);	
		kill(currentIndex);
	}
	
	/*
	 * this method walk randomly until it reaches a dead end, then it calls the hunt method
	 */
	public void kill(int index) {
		int currentIndex = index;														//start with random cell
		
		while(true) {
			cells[currentIndex].visited = true;
		
			int nextIndex = getRandomUnvisitedNeighbourIndex(currentIndex);
		
			if(nextIndex == NOT_EXIST) {
				hunt(); 																//if there is NO neighbors [DEAD END]
				break;
			}
			else if(currentIndex - nextIndex == -1) {
				cells[currentIndex].border[EAST] = NO_WALL; 							//neighbor is on the right 
				cells[nextIndex].border[WEST] = NO_WALL;
			}
			else if(currentIndex - nextIndex == width) {
				cells[currentIndex].border[NORTH] = NO_WALL;							//neighbor is up
				cells[nextIndex].border[SOUTH] = NO_WALL;
			}
			else if(currentIndex - nextIndex == -width) {								
				cells[currentIndex].border[SOUTH] = NO_WALL;							//neighbor is down
				cells[nextIndex].border[NORTH] = NO_WALL;
			}
			else if(currentIndex - nextIndex == 1) {									
				cells[currentIndex].border[WEST] = NO_WALL;								//neighbor is left
				cells[nextIndex].border[EAST] = NO_WALL;
			}
			currentIndex = nextIndex;
		}
	}
	
	
	/*
	 * returns a random index neighbor from list of unvisited neighbors of the specified cell 
	 */
	private Integer getRandomUnvisitedNeighbourIndex(int currentIndex) {
		
		ArrayList<Integer> neighbors = setUnvisitedNeighbors(currentIndex);
		
		Random random = new Random();
				
		return neighbors.size() == 0 ? NOT_EXIST : neighbors.get(random.nextInt(neighbors.size())); 
	}

	
	/*
	 * This method returns an arraylist of neighbors indices not visited for the specified cell 
	 */
	private ArrayList<Integer> setUnvisitedNeighbors(int currentIndex) {
		
		int row = currentIndex / width;												//number of row
		int column = currentIndex % width;											//number of column
		
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		
		if(row != 0 && !cells[currentIndex - width].visited ) {						// cell has upper neighbor
			int upperIndex = ((row - 1) * width) + column;
			neighbors.add(upperIndex);
		}
		if(row != height - 1 && !cells[currentIndex + width].visited ) {			//cell has lower neighbor
			int lowerIndex = ((row + 1) * width) + column;
			neighbors.add(lowerIndex);
		}
		if(column != 0 && !cells[currentIndex - 1].visited) {						//cell has left neighbor
			int leftIndex = currentIndex - 1;
			neighbors.add(leftIndex);
		}
		if(column != width - 1 && !cells[currentIndex + 1].visited) {				//cell has right neighbor
			int rightIndex = currentIndex + 1;
			neighbors.add(rightIndex);
		}
		return neighbors;
	}

/*****************************************************************************************************/
	/*
	 * this method finds an unvisited cell which has at least one visited neighbor, makes a connection between them 
	 * and then calls the kill function on the neighbor cell.
	 */
	public void hunt() {
		for(int i = 0; i < N; i++) {
			if(cells[i].visited == false) {
				
				int currentIndex = i;
				
				ArrayList<Integer> neighbors = setVisitedNeighbors(i);
				int nextIndex = getRandomVisitedNeighborIndex(neighbors);
								
				if(neighbors.size() != 0 ) {
					cells[currentIndex].visited = true;
					
					if(nextIndex == NOT_EXIST) {
						break; 																	//if there is NO not visited neighbors 
					}
					else if(currentIndex - nextIndex == -1) {
						cells[currentIndex].border[EAST] = NO_WALL; 							//neighbor is right 
						cells[nextIndex].border[WEST] = NO_WALL;
					}
					else if(currentIndex - nextIndex == width) {
						cells[currentIndex].border[NORTH] = NO_WALL;							//neighbor is up
						cells[nextIndex].border[SOUTH] = NO_WALL;
					}
					else if(currentIndex - nextIndex == -width) {								
						cells[currentIndex].border[SOUTH] = NO_WALL;							//neighbor is down
						cells[nextIndex].border[NORTH] = NO_WALL;
					}
					else if(currentIndex - nextIndex == 1) {									
						cells[currentIndex].border[WEST] = NO_WALL;								//neighbor is left
						cells[nextIndex].border[EAST] = NO_WALL;
					}
					kill(nextIndex);
				}
			}
		}
	}


	private int getRandomVisitedNeighborIndex(ArrayList<Integer> neighbors) {
		Random random = new Random();
		return neighbors.size() == 0 ? NOT_EXIST : neighbors.get(random.nextInt(neighbors.size())); 
	}
	
	private ArrayList<Integer> setVisitedNeighbors(int currentIndex) {
		
		int row = currentIndex / width;												//number of row
		int column = currentIndex % width;											//number of column
		
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		
		if(row != 0 && cells[currentIndex - width].visited ) {						// cell has upper neighbor
			int upperIndex = ((row - 1) * width) + column;
			neighbors.add(upperIndex);
		}
		if(row != height - 1 && cells[currentIndex + width].visited ) {				//cell has lower neighbor
			int lowerIndex = ((row + 1) * width) + column;
			neighbors.add(lowerIndex);
		}
		if(column != 0 && cells[currentIndex - 1].visited) {						//cell has left neighbor
			int leftIndex = currentIndex - 1;
			neighbors.add(leftIndex);
		}
		if(column != width - 1 && cells[currentIndex + 1].visited) {				//cell has right neighbor
			int rightIndex = currentIndex + 1;
			neighbors.add(rightIndex);
		}
		return neighbors;
	}
	
	private void printCells() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.print(cells[i].border[j]);
			}
			System.out.println();
		}
	}
	
	public void drawMaze(Graphics g) {
		g.setColor(Color.BLACK);

		marginHorizental = ((MazeGUI.frameWidth - (width * CELL_WIDTH)) / 2) - 10;
		int h = ((MazeGUI.panelHeight - (height * CELL_WIDTH)) / 2) - 10; 
		
		for(int i = 0; i < N; i++) {
			int row = i / width;											//number of row
			int column = i % width;											//number of column	
			
			if(cells[i].border[NORTH] == WALL) {
				g.drawLine(marginHorizental + (CELL_WIDTH * column) , h + ((row) * CELL_WIDTH), 
						marginHorizental + (CELL_WIDTH * (column + 1)), h + ((row) * CELL_WIDTH));
			}
			if(cells[i].border[EAST] == WALL) {
				g.drawLine(marginHorizental + (CELL_WIDTH * (column + 1)), h + ((row) * CELL_WIDTH),
						marginHorizental + (CELL_WIDTH * (column + 1)), h + ((row + 1) * CELL_WIDTH));
			}
			if(cells[i].border[SOUTH] == WALL) {
				g.drawLine(marginHorizental + (CELL_WIDTH * (column + 1)), h + ((row + 1) * CELL_WIDTH),
						marginHorizental + (CELL_WIDTH * column), h + ((row + 1) * CELL_WIDTH));
			}
			if(cells[i].border[WEST] == WALL) {
				g.drawLine(marginHorizental + (CELL_WIDTH * column), h + ((row + 1) * CELL_WIDTH),
						marginHorizental + (CELL_WIDTH * column) , h + ((row) * CELL_WIDTH));
			}
		}
	}

}
