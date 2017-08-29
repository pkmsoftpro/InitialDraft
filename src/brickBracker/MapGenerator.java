package brickBracker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/*
 * author: prashant.m
 * date: 27 Aug 2017
 */

//
public class MapGenerator {
	
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	public MapGenerator(int row, int col){
		map = new int[row][col];
		for(int i = 0; i < map.length; i++){
			for(int j=0; j < map[0].length; j++){
				/*
				 * adding 1 to all the elements of map, which means that we want the these brick.
				 * if we need to remove any brick when ball hits it, we just need to change the value of 
				 * that map element to 0.
				 */
				map[i][j] = 1;
			}
		}
		brickWidth = 540/col;
		brickHeight = 150/row;
	}
	//a method for drawing the bricks
	public void draw(Graphics2D g){
		for(int i = 0; i < map.length; i++){
			for(int j=0; j < map[0].length; j++){
				if(map[i][j] > 0){
					g.setColor(Color.BLUE);
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.BLACK);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
	}
	
	//for the intersection of ball and brick, if 0 then delete brick, if 1 then brick is visible
	public void setBrickValue(int value, int row, int col){
		map[row][col] = value;
	}
}
