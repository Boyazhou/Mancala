/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.01 2015/4/27
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.*;

/**
 * this is the Board Frame for the project
 * @author Team Architects
 */
public class BoardFrame extends JFrame
{
	private Board board;
	private Mancala mancalaA;
	private Mancala mancalaB;
	private PitPanel pitController;
	private MainFrame mainFrame;
	private JPanel redoPanel;

	public final static int DEFAULT_WIDTH = 1000;
	public final static int DEFAULT_HEIGHT = 300;

	public BoardFrame(Board board)
	{
		this.board = board;
		this.mainFrame = mainFrame;
		setLayout(new BorderLayout());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x_centered = (int) screenSize.getWidth()/2 - BoardFrame.DEFAULT_WIDTH/2;
		int y_centered = (int) screenSize.getHeight()/2 - BoardFrame.DEFAULT_HEIGHT/2;

		setBounds(
			x_centered, 
		  	y_centered, 
		  	BoardFrame.DEFAULT_WIDTH, 
		  	BoardFrame.DEFAULT_HEIGHT
	    );

		pitController = new PitPanel(board);
		mancalaA = new Mancala(board, Board.MANCALA_A);
		mancalaB = new Mancala(board, Board.MANCALA_B);
		
		//set ChangeListener for MancalaA
		ChangeListener listenerA = new //controller
				ChangeListener()
				{
						public void stateChanged(ChangeEvent e)
						{
							//System.out.println(mancalaA.getmancala());
							mancalaA.setmancala(board.getNumOfStones(board.MANCALA_A_HOLE) );
							
							repaint();
						}
				};
		
		//set ChangeListener for MancalaB
		ChangeListener listenerB = new //controller
				ChangeListener()
				{
						public void stateChanged(ChangeEvent e)
						{
							//System.out.println(mancalaA.getmancala());
							mancalaB.setmancala(board.getNumOfStones(board.MANCALA_B_HOLE) );
							
							repaint();
						}
				};
		//add ChangeListener to the board class
		board.attach(listenerA);
		board.attach(listenerB);

		//add the Undo panel
		redoPanel = new JPanel();
		JButton redoBtn = new JButton("REDO");
		//controller
		redoBtn.addActionListener(new ActionListener(){ 
			public void actionPerformed(ActionEvent e) {
				board.redo();
			}
		});
		
		JButton undoBtn = new JButton("UNDO");
		//controller
		undoBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				board.undo();
			}
		});

		JButton quitButton = getQuitButton();

		redoPanel.add(redoBtn);
		redoPanel.add(undoBtn);
		redoPanel.add(quitButton);
		
		//display the turn for player
		JTextArea playerTurn = new JTextArea();
		playerTurn.setBounds(120, 10, 150, 20);
		add(playerTurn);

		ChangeListener listenerC = new //controller
				ChangeListener()
				{
						public void stateChanged(ChangeEvent e)
						{
							playerTurn.setText("Next Move: " + board.getplayerturn()); 
							repaint();
						}
				};
		//add ChangeListener to the board class
		board.attach(listenerC);
		
		add(redoPanel, BorderLayout.NORTH);
		add(pitController, BorderLayout.CENTER);

		JPanel mancalaAPanel = new JPanel(new BorderLayout());
		mancalaAPanel.add(new JLabel("Mancala A", SwingConstants.CENTER), BorderLayout.SOUTH);
		mancalaAPanel.add(mancalaA, BorderLayout.CENTER);

		JPanel mancalaBPanel = new JPanel(new BorderLayout());
		mancalaBPanel.add(new JLabel("Mancala B", SwingConstants.CENTER), BorderLayout.SOUTH);
		mancalaBPanel.add(mancalaB, BorderLayout.CENTER);

		add(mancalaAPanel, BorderLayout.EAST);
		add(mancalaBPanel, BorderLayout.WEST); 

		add(getCopyrightInformation(), BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(false);
	}
	
	public void setBoardLayout(BoardLayout layout)
	{		
		mancalaA.setMancalaLayout(layout);
		mancalaB.setMancalaLayout(layout);
		pitController.setPanelLayout(layout);
		repaint();
	}

	private JLabel getCopyrightInformation()
	{
		JLabel copyrightInformation = new JLabel(
			"<html>" + 
				"<body>" + 
					"<br><br><h4><center>© 2015 Team Architects. All Rights Reserved.</center></h4>" +
				"</body>" +
			"</html>"
		, SwingConstants.CENTER);

		return copyrightInformation;
	}

	/**
	 * Get quit button to exit the program
	 * @return a button containing action to quit the program.
	 */
	private JButton getQuitButton()
	{
		JButton quitButton = new JButton("Quit");
		quitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		return quitButton;
	}
}