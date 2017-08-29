package brickBracker;

import javax.swing.JFrame;
/*
 * author: prashant.m
 * date: 29 Aug 2017
 */

public class BreakOutMain {
	
	public static void main(String[] args) {
		/*
		 * going to create the background
		 */
		JFrame obj = new JFrame();
		Gameplay gamePlay = new Gameplay();
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Breakout in Swing");
		obj.setResizable(false);
		obj.setVisible(true);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
	}
}
