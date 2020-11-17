package mazeAI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MazeGUI {
	
	JFrame frame;
	MazeGenerator maze;
	MazePanel mazePanel;
	JPanel sidePanel;
	JButton btnShowSolution, btnNewMaze, btnEdit;
	public int width, height;
	
	public static final int FRAME_WIDTH = 600;
	public static final int FRAME_HEIGHT = 600;
	public static int panelHeight = 20;
	public static int frameWidth = 600;

	
	public MazeGUI(int height, int width) {
		this.height = height;
		this.width = width;
        frame = new JFrame();
			    
		mazePanel = new MazePanel();
		mazePanel.setLayout(new BorderLayout());
		
		sidePanel = new JPanel();
		setSidePanel();
		
		JScrollPane scroll = new JScrollPane(mazePanel);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        //scroll.getViewport().setPreferredSize(new Dimension(512, 448));
        //scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  
        Container pane = frame.getContentPane();
        pane.add(scroll);
        
		setFrame(frame, pane, sidePanel);
		maze = new MazeGenerator(width, height);
		mazePanel.setMaze(maze);
	}

	private void setSidePanel() {
		setButtons();
		
		sidePanel.setBackground(Color.WHITE);
		sidePanel.add(btnShowSolution);
		sidePanel.add(btnNewMaze);
		sidePanel.add(btnEdit);
	}

	private void setButtons() {
		btnNewMaze = new JButton("NEW MAZE");
		btnShowSolution = new JButton("Show Solution");
		btnEdit = new JButton("Edit");
		
		btnNewMaze.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				maze = new MazeGenerator(width, height);
				mazePanel.setMaze(maze);
			}
		});
		
		btnShowSolution.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(maze != null) {
					Cell[] cells = maze.getCells();
					Cell start = cells[0];
					Cell goal = cells[cells.length - 1];
					Solver sol=new Solver(start, goal, cells, maze.width, maze.height);
					mazePanel.setSolution(sol);
				}
			}
		});
		
		btnEdit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showInputDialog();
			}

			private void showInputDialog() {
		        JTextField txtwidth = new JTextField();
		        final JTextField txtheight = new JTextField();


		        Object[] inputFields = {
		        		"Width", txtwidth,
		                "Length", txtheight
		                };
		        
		        int option = JOptionPane.showConfirmDialog(frame, inputFields, "Multiple Inputs", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

		        if (option == JOptionPane.OK_OPTION) {
		        	if(startFrame.isInteger(txtwidth.getText()) && startFrame.isInteger(txtheight.getText())){
			            int widtht = Integer.parseInt(txtwidth.getText());
			            int heightt = Integer.parseInt(txtheight.getText());
			            width = widtht;
			            height = heightt;
			            maze = new MazeGenerator(widtht, heightt);
						mazePanel.setMaze(maze);
		        	}		            
		        }
			}
		});
		btnNewMaze.setBackground(Color.WHITE);
		btnNewMaze.setFont(new java.awt.Font("Impact", java.awt.Font.PLAIN, 20));
		
		btnShowSolution.setBackground(Color.WHITE);
		btnShowSolution.setFont(new java.awt.Font("Impact", java.awt.Font.PLAIN, 20));
		
		btnEdit.setBackground(Color.WHITE);
		btnEdit.setFont(new java.awt.Font("Impact", java.awt.Font.PLAIN, 16));
	}

	private void setFrame(JFrame frame, Container pane, JPanel sidePanel) {
		frame.setTitle("Maze");
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(pane, BorderLayout.CENTER);
		frame.add(sidePanel, BorderLayout.SOUTH);
		frame.setVisible(true);
		frame.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
				panelHeight = mazePanel.getHeight();
				frameWidth = frame.getWidth();
				mazePanel.rePaint();
		    }
		});
	}
	
}

class MazePanel extends JPanel
{	
	MazeGenerator maze;
	Solver sol;

	public MazePanel() {
		
	}
	
	public MazePanel(MazeGenerator m) {
		this.maze = m;
	}

	public MazePanel(MazeGenerator m, Solver s) {
		this.maze = m;
		this.sol=s;
	}
	
	public void setMaze(MazeGenerator maze) {
		this.maze = maze;
		sol = null;
		repaint();
	}
	
	public void setSolution(Solver sol) {
		this.sol = sol;
		repaint();
	}
	
	public void rePaint() {
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setPreferredSize(getPreferredSize());
		this.setBackground(Color.WHITE);
		maze.drawMaze(g);
		if(sol != null) {
			sol.drawSolution(g);
		}
	}
}

