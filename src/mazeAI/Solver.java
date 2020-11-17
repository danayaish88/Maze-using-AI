/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeAI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aya
 */
public class Solver {

	Cell startCell;
	Cell goalCell;
	Cell bestCell;
	int width;
	int height;
	Cell[] cells;

	List <Cell> openL;
	List <Cell> closedL;
	List <Cell> solution;

	public Solver(Cell start,Cell goal, Cell[] cells, int width, int height) 
	{
		this.startCell = start;
		this.startCell.setParent(null);
		this.goalCell = goal;
		this.cells = cells;
		this.width=width;
		this.height=height;
		
		openL=new ArrayList<Cell>();
		closedL=new ArrayList<Cell>();
		solution=new ArrayList<Cell>();

		aStar();
	}

	/*this function for calculating manhatance distance which is the heuristic function*/
	public static int manhatten_distance(Cell w,Cell g)
	{
		int x0=w.getRow();
		int x1=g.getRow();
		int y0=w.getColumn();
		int y1=g.getColumn();
		return Math.abs(x1-x0)+ Math.abs(y1-y0);
	}


	/*function for tie breaker according to the least h function first
	 * if more than one have the same h we put them in another arraylist
	 * and return the first  element of it
	 */
	public static Cell tie_breaker(List <Cell> CellList)
	{
		List <Cell> equalCells=new ArrayList<Cell>();
		Cell t = CellList.get(0);

		for(int i = 1; i < CellList.size(); i++)
		{
			if(CellList.get(i).getHn() < t.getHn())
			{
				t=CellList.get(i);
			}

		}

		for(int i = 0; i < CellList.size(); i++)
		{
			if(CellList.get(i).getHn() == t.getHn())
			{
				equalCells.add(CellList.get(i));
			}

		}


		return equalCells.get(0);
	}

	/* a function to check if any two given Cells are the same according to the x and 
y values we use it in the code to check if we reached the goal or not*/
	public static boolean isEqualCells(Cell f,Cell g)
	{
		if((f.getRow() == g.getRow()) && (f.getColumn() == g.getColumn()))
			return true;
		else 
			return false;
	}

	/* checking if a Cell is in the open or closed list */
	public boolean isInCloseOrOpenLists(Cell n)
	{   

		for(int i = 0; i < openL.size(); i++)
		{
			if((n.getRow() == openL.get(i).getRow()) && (n.getColumn() == openL.get(i).getColumn()))
				return true;
		}

		for(int i = 0; i < closedL.size(); i++)
		{
			if((n.getRow() == closedL.get(i).getRow()) && (n.getColumn() == closedL.get(i).getColumn()))
				return true;
		}

		return false;
	}

	/* to check if the path of a visited Cell befor better than the new one or not*/
	public boolean newPathIsBetter(Cell n)
	{   
		for(int i = 0; i < openL.size(); i++)
		{
			if((n.getRow() == openL.get(i).getRow()) && (n.getColumn() == openL.get(i).getColumn())
					&& ((n.getFn()) < openL.get(i).getFn()))
			{ 
				openL.removeIf(n1-> ((n1.getRow() == n.getRow()) && (n1.getColumn() == n.getColumn())));
				openL.add(n);
				return true;
			}
		}

		for(int i = 0; i < closedL.size(); i++)
		{
			if((n.getRow() == closedL.get(i).getRow()) && (n.getColumn() == closedL.get(i).getColumn())
					&& ((n.getFn()) < closedL.get(i).getFn()))
			{
				closedL.removeIf(n1-> ((n1.getRow() == n.getRow()) && (n1.getColumn() == n.getColumn())));
				openL.add(n);
				return true;

			}    
		}

		return false;
	}


	public void printt()
	{
		for(int i = 0; i < openL.size(); i++)
			System.out.println(openL.get(i).getRow() + "," + openL.get(i).getColumn());
	}

	/*
	 * returns the best Cell in openList
	 * a function to choose the smallest f function in the openlist to expand after finding it if we 
	 *have two or more Cells have the same f we pass the arraylist contains them to the tie breaker fun
	 */
	public Cell bestInOpenList()
	{                               

		List <Cell> bstL=new ArrayList<Cell>();

		Cell bst=openL.get(0);
		for(int i = 1; i < openL.size(); i++)
		{
			if(openL.get(i).getFn() < bst.getFn())
				bst = openL.get(i);
		}

		for(int i = 0; i < openL.size(); i++)
		{
			if(openL.get(i).getFn() == bst.getFn())
				bstL.add(openL.get(i));
		}

		if(bstL.size() > 1)
			bst=tie_breaker(bstL);


		return bst;
	}

	/*filling the solution arraylist*/
	public List<Cell> solution(Cell bst)
	{  solution.add(goalCell);

	if(bst.getParent() == null)  
		return solution;
	else 
	{
		solution.add(bst.getParent());
		solution(bst.getParent());
		return solution;
	}

	}

	/*the function of the algorithm*/
	public void aStar ()
	{   
		openL.add(startCell);								//adding the first state to be expand in the open list which is row 0 in traditional table
		boolean bet;  										//checking if the bath is better 

		do
		{              
			if(openL.size() == 0)							//no solution case
			{
				break;
			}

			bestCell = bestInOpenList();

			if ((isEqualCells(bestCell, goalCell)))			//reached the goal
			{           
				solution(bestCell);
				break;
			}
			else
			{         
				/* all the if statments below for checking which children to expand if any exists*/
				if(bestCell.border[MazeGenerator.WEST] == MazeGenerator.NO_WALL)
				{                  
					int position=((bestCell.getRow()*width)+bestCell.getColumn())-1;

					Cell subCell=cells[position];
			
					int dist=manhatten_distance(subCell,goalCell);
					int cost=bestCell.getGn()+1;
					
					if(!isInCloseOrOpenLists(subCell))
					{   
						subCell.setParent(bestCell);
						openL.add(subCell);
						subCell.setHn(dist);
						subCell.setGn(cost);
					}
					else
					{
						if(newPathIsBetter(subCell))
						{
							subCell.setParent(bestCell);
							subCell.setHn(dist);
							subCell.setGn(cost);
						}
					}          
				}
				if(bestCell.border[MazeGenerator.EAST] == MazeGenerator.NO_WALL)
				{
					int position=((bestCell.getRow() * width) + bestCell.getColumn()) + 1;
					Cell E=cells[position];
					
					int dist = manhatten_distance(E, goalCell);
					int cost = bestCell.getGn() + 1;
					 
					if(!isInCloseOrOpenLists(E))
					{   
						E.setParent(bestCell);
						openL.add(E);
						E.setHn(dist);
						E.setGn(cost);
			
					}
					else
					{
						if(newPathIsBetter(E))
						{
							E.setParent(bestCell);
							E.setHn(dist);
							E.setGn(cost);
						}
					}          

				}

				if(bestCell.border[MazeGenerator.SOUTH] == MazeGenerator.NO_WALL)
				{
					int position = ((bestCell.getRow() * width) + bestCell.getColumn()) + width;
					Cell s = cells[position];
					
					int dist = manhatten_distance(s, goalCell);
					int cost = bestCell.getGn() + 1;
					
					if(!isInCloseOrOpenLists(s))
					{   
						s.setParent(bestCell);
						s.setHn(dist);
						s.setGn(cost);
						openL.add(s);
					}
					else
					{
						if(newPathIsBetter(s))
						{ 
							s.setParent(bestCell);
							s.setHn(dist);
							s.setGn(cost);
						}
					}          
				}

				if(bestCell.border[MazeGenerator.NORTH] == MazeGenerator.NO_WALL) //Cell.border[NORTH] == NO_WALL
				{
					int position = ((bestCell.getRow() * width) + bestCell.getColumn()) - width;
					Cell N = cells[position];
					int dist = manhatten_distance(N, goalCell);
					int cost=bestCell.getGn() + 1;

					if(!isInCloseOrOpenLists(N))
					{   
						N.setParent(bestCell);
						N.setHn(dist);
						N.setGn(cost);
						openL.add(N);
					}
					else
					{
						if(newPathIsBetter(N))
						{
							N.setParent(bestCell);
							N.setHn(dist);
							N.setGn(cost);
						}
					}          

				}
			}
			closedL.add(bestCell);
			openL.removeIf(n-> ((n.getRow() == bestCell.getRow()) && (n.getColumn() == bestCell.getColumn())));
			//printt();
		}while(true);
	}

	public void drawSolution(Graphics g)
	{
		int marginHorizental = MazeGenerator.marginHorizental;
		int marginVertical = ((MazeGUI.panelHeight - (height * MazeGenerator.CELL_WIDTH)) / 2) - 10;
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(4));
	    g2.setColor(Color.GREEN);
	    for(int i = 0; i < solution.size() - 1; i++) 
	    {
	    	if(solution.get(i).getParent()!=null)
			{
	    		g2.draw(new Line2D.Float((solution.get(i).getColumn() * MazeGenerator.CELL_WIDTH) + marginHorizental + (MazeGenerator.CELL_WIDTH/2),
	    				(solution.get(i).getRow() * MazeGenerator.CELL_WIDTH) + marginVertical + (MazeGenerator.CELL_WIDTH/2),
	    				(solution.get(i).getParent().getColumn() * MazeGenerator.CELL_WIDTH) + marginHorizental + (MazeGenerator.CELL_WIDTH/2),
	    				(solution.get(i).getParent().getRow() * MazeGenerator.CELL_WIDTH) + marginVertical + (MazeGenerator.CELL_WIDTH/2)));
			}
	    }
	}
}
