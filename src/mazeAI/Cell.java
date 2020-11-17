package mazeAI;

public class Cell {
	
	public Boolean visited;
	public int border[];
	private int row;
	private int column;
    private int hn = 0;       //h(n) heuristic fun       
    private int gn = 0;       //g(n) cost fun
    private int fn = 0;       //h + g
    private Cell parent;
	
	
	public Cell(int row, int column) {
		this.row = row;
		this.column = column;
		visited = false;
		border = new int[4];
		initBorders();
	}

	private void initBorders() {

		for(int i = 0; i < 4; i++) {
			border[i] = MazeGenerator.WALL;
		}
	}
	
	public void setGn(int gn) 
    {
        this.gn = gn;
        this.setFn(gn, hn);
    }
    
    public void setHn(int h) {
        this.hn = h;
        this.setFn(this.gn,this.hn);
    }
    
    public void setFn(int gn,int hn) {
        this.fn = gn+hn;
    }
    
    public void setRow(int row) {
    	this.row = row;
    }
    
    public void setColumn(int column) {
    	this.column = column;
    }
    
    public int getHn() {
        return hn;
    }

    public int getGn() {
        return gn;
    }

    public int getFn() {
        return fn;
    }
    
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
    
    public Cell getParent() {
        return parent;
    }

    public void setParent(Cell parent) 
    {
        this.parent = parent;
    }
	

}
