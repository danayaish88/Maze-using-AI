package mazeAI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLayeredPane;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class startFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldWidth;
	private JTextField textFieldHeight;
	private int height = 0;
	private int width = 0;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					startFrame frame = new startFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public startFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(0, 0, 564, 450);
		lblNewLabel.setIcon(new ImageIcon("D:\\Documents\\Desktop\\3a6242d1e37af9855e696c2e9e6b8ce9.jpg"));
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("HELLO, Ready?");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new java.awt.Font("Impact", java.awt.Font.PLAIN, 30));
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBounds(169, 89, 212, 56);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblWidth = new JLabel("Width");
		lblWidth.setForeground(Color.WHITE);
		lblWidth.setFont(new java.awt.Font("Impact", java.awt.Font.PLAIN, 20));
		lblWidth.setBounds(107, 218, 63, 18);
		contentPane.add(lblWidth);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setForeground(Color.WHITE);
		lblHeight.setFont(new java.awt.Font("Impact", java.awt.Font.PLAIN, 20));
		lblHeight.setBounds(397, 218, 63, 18);
		contentPane.add(lblHeight);
		
		textFieldWidth = new JTextField();
		textFieldWidth.setBackground(Color.WHITE);
		textFieldWidth.setForeground(Color.BLACK);
		textFieldWidth.setBounds(85, 247, 96, 20);
		contentPane.add(textFieldWidth);
		textFieldWidth.setColumns(10);
		
		textFieldHeight = new JTextField();
		textFieldHeight.setBounds(375, 247, 96, 20);
		contentPane.add(textFieldHeight);
		textFieldHeight.setColumns(10);
		
		JButton btnNewButton = new JButton("GO");
		btnNewButton.addMouseListener(new MouseAdapter() {
			boolean heightIsSet = false;
			boolean widthIsSet = false;
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textFieldWidth.getText().isBlank()) {
					textFieldWidth.setBorder(BorderFactory.createLineBorder(Color.RED));				
					}
				else {
					textFieldWidth.setBorder(BorderFactory.createLineBorder(Color.WHITE));				

				}
				if(textFieldHeight.getText().isBlank()) {
					textFieldHeight.setBorder(BorderFactory.createLineBorder(Color.RED));				
				}
				else {
					textFieldHeight.setBorder(BorderFactory.createLineBorder(Color.WHITE));				
				}
				if(!textFieldHeight.getText().isBlank() && !textFieldWidth.getText().isBlank()) {
					
					if(isInteger(textFieldHeight.getText())) {
						height = (int)Double.parseDouble(textFieldHeight.getText());
						heightIsSet = true;
					}else {
						textFieldHeight.setBorder(BorderFactory.createLineBorder(Color.RED));				
					}
					
					if(isInteger(textFieldWidth.getText())) {
						width = (int)Double.parseDouble(textFieldWidth.getText());
						widthIsSet = true;
					}else {
						textFieldWidth.setBorder(BorderFactory.createLineBorder(Color.RED));				
					}				
					}
				if(heightIsSet && widthIsSet) {
					dispose();
					MazeGUI mazeGUI = new MazeGUI(height, width);
				}
				
			}
		});
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setFont(new java.awt.Font("Impact", java.awt.Font.PLAIN, 20));
		btnNewButton.setBounds(235, 305, 89, 35);
		contentPane.add(btnNewButton);
		contentPane.add(lblNewLabel);
		
		
	}
	
	public static boolean isInteger(String s) {
		try {
			double reqNum = Double.parseDouble(s);
		}
		catch(NullPointerException e) {
			return false;
		}
		catch(NumberFormatException e ) {
			return false;
		}
		return true;
	}
	
}
