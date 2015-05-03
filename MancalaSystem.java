/**
 *	COPYRIGHT (C) 2015 Team Architects. All Rights Reserved.
 *	Mancala
 *	CS 151 Project Solution
 *	@author Boya Zhou, Edwin Limantara, Kun Su
 *	@version 1.01 2015/4/27
 */

import java.util.Scanner;

public class MancalaTest 
{	
	public static void main(String[] args)
	{
		Board board = new Board();
		BoardFrame b = new BoardFrame(board);
		MainFrame m = new MainFrame(b);
	}
}
