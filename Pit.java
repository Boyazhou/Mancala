/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.01 2015/4/27
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

/**
 * this is one of the Component for PitPanel
 * @author Team Architects
 */
//Controller: Back side -> see PitPanel (Controller: Front side)
public class Pit extends JComponent implements ChangeListener
{
	public static int DEFAULT_STONES_NUM = 3;

	public static void setDefaultStone(int n)
	{
		DEFAULT_STONES_NUM = n;
	}

	private int id;
	private Board board;
	private BoardLayout layout;

	private static final int DEFAULT_WIDTH = 60;
	private static final int DEFAULT_HEIGHT = 60;
	
	private final static int DEFAULT_STONE_X = 0;
	private final static int DEFAULT_STONE_Y = 0;
	private final static int LEFT_PADDING = 10;
	private final static int RIGHT_PADDING = 10;
	private final static int TOP_PADDING = 10;
	private final static int DEFAULT_STONE_SIZE = 10;

	public Pit(int id, Board board)
	{	
		this.board = board;
		this.id = id;
	}

	public void stateChanged(ChangeEvent e)
	{
		board = (Board) e.getSource();
		repaint();
	}
	
	public void setPitLayout(BoardLayout layout)
	{
		this.layout = layout;
		repaint();
	}

	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		int x_centered = getWidth()/2 - Pit.DEFAULT_WIDTH/2;
		int y_centered = getHeight()/2 - Pit.DEFAULT_HEIGHT/2;

		setPreferredSize(new Dimension(
			Pit.DEFAULT_WIDTH, 
			Pit.DEFAULT_HEIGHT
		));

		g2.draw(layout.pitsLayout(x_centered, y_centered, Pit.DEFAULT_WIDTH, Pit.DEFAULT_HEIGHT));

		// Create the parts of this car and draw them here.
		int Xcircle = Pit.DEFAULT_STONE_X;
		int Ycircle = Pit.DEFAULT_STONE_Y;
		for(int i = 0; i < board.getNumOfStones(id); i++)
		{
			//create Pit inside the Rectangle, MAX Pit will be 36.
			Ellipse2D.Double circle = new Ellipse2D.Double ( 
				x_centered + Xcircle + LEFT_PADDING, 
				y_centered + Ycircle + TOP_PADDING, 
				Pit.DEFAULT_STONE_SIZE, 
				Pit.DEFAULT_STONE_SIZE
			); 

			g2.fill(circle);

			Xcircle += Pit.DEFAULT_STONE_SIZE;

			if(Xcircle >= DEFAULT_WIDTH - LEFT_PADDING - RIGHT_PADDING)
			{
				Xcircle = DEFAULT_STONE_X;
				Ycircle += Pit.DEFAULT_STONE_SIZE;
			}
		}
		
		//label sets up
		if(id<6)
		{
			g2.drawString("A" + String.valueOf(id+1), 10, 30);
		}
		else
		{
			g2.drawString("B" + String.valueOf(id-5), 10, 30);
		}
	}

	public boolean contains(int x, int y)
	{
		int row = getRow();
		int col = getCol();
		int outerWidth = getWidth();
		int outerHeight = getHeight();

		int x0 = (col*outerWidth) + (outerWidth/2) - (Pit.DEFAULT_WIDTH/2);

		int y0 = ((PitPanel.DEFAULT_ROWS_NUMBER - row - 1)*outerHeight) + 
				 (outerHeight/2) - (Pit.DEFAULT_HEIGHT/2);

		return x >= x0 &&
			   x <= x0 + Pit.DEFAULT_WIDTH &&
			   y >= y0 &&
			   y <= y0 + Pit.DEFAULT_HEIGHT;
	}

	private int getRow()
	{
		return id / PitPanel.DEFAULT_COLS_NUMBER;
	}

	private int getCol()
	{
		if(id >= PitPanel.FIRST_UPPER_PIT && id <= PitPanel.LAST_UPPER_PIT)
		{
			return PitPanel.LAST_LOWER_PIT - (id % PitPanel.DEFAULT_COLS_NUMBER);
		}
		else if(id <= PitPanel.LAST_LOWER_PIT)
		{
			return id;
		}
		else
		{
			// throw error later
			return -1;
		}
	}
}