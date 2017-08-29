package brickBracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * author: prashant.m
 * date: 27 Aug 2017
 */

public class Gameplay extends JPanel implements KeyListener, ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4483875793049264088L;
	/*
	 * so that when our game starts it shouldn't play by itself
	 */
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 14;
	
	private Timer timer;
	private int delay = 7;
	
	private int playerX = 310;
	
	private int ballposX = 120;
	private int ballposY= 350;
	private int ballXdir = -1;
	private int ballYdir= -2;
	
	private MapGenerator mapper;
	
	public Gameplay(){
		mapper = new MapGenerator(2, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();		
	}
	
	public void paint(Graphics g) {
		//add background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(1, 1, 692, 592);
		
		//draw brick map
		mapper.draw((Graphics2D)g);
		
		//border
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 3, 592); //left
		g.fillRect(0, 0, 692, 3); //top
		g.fillRect(691, 0, 3, 592); //right
		
		//author
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif", Font.BOLD, 12));
		g.drawString("Author: Prashant, Anvesh, Seohyun", 5, 30);
		
		//scores
		g.setColor(Color.WHITE);
		g.setFont(new Font("serif", Font.BOLD, 12));
		g.drawString("Score: "+score, 500, 30);
		
		//stop watch
		Calendar cal = new GregorianCalendar();
		String hour = String.valueOf(cal.get(Calendar.HOUR));
		String minute = String.valueOf(cal.get(Calendar.MINUTE));
		String second = String.valueOf(cal.get(Calendar.SECOND));
		g.setFont(new Font("serif", Font.BOLD, 12));
		if(play && !(ballposY > 570)){
			g.drawString("Time: " + hour + ":" + minute + ":" + second, 590, 30);
		}
		
		//paddle
		g.setColor(Color.BLUE);
		g.fillRect(playerX, 550, 100, 8);
		
		//ball
		g.setColor(Color.CYAN);
		g.fillOval(ballposX, ballposY, 20, 20);	
		
		//game won
		if(totalBricks <= 0){
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won, Score: "+score, 200, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to restart", 230, 350);
		}
		
		//game over display
		if(ballposY > 570){
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			
			g.setColor(Color.RED);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: "+score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to restart", 230, 350);
		}
		
		g.dispose();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play){
			/*
			 * adding a code for the intersection of ball with the paddle
			 */
			if(new Rectangle(ballposX,ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
				ballYdir = -ballYdir;
			}
			
			/*
			 * code for the intersection of ball with the bricks
			 */
			A: for(int i=0; i < mapper.map.length; i++){
				for(int j=0; j < mapper.map[0].length; j++){
					if(mapper.map[i][j] > 0){
						int brickX = j * mapper.brickWidth + 80;
						int brickY = i * mapper.brickHeight + 50;
						int brickWidth = mapper.brickWidth;
						int brickHeight = mapper.brickHeight;
						
						Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						
						if(ballRect.intersects(brickRect)){
							mapper.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
								ballXdir = -ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
							break A;
						}
					}
				}
			}
			
			/*
			 * this is for the movement of ball only. and not for intersection with the paddle
			 */
			ballposX += ballXdir;
			ballposY += ballYdir;
			//left border
			if(ballposX < 0){
				ballXdir = -ballXdir;
			}
			//top
			if(ballposY < 0){
				ballYdir = -ballYdir;
			}
			//right
			if(ballposX > 670){
				ballXdir = -ballXdir;
			}
		}
		
		repaint();		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			//paddle shouldn't go outside the panel
			if(playerX >= 600){
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			//paddle shouldn't go outside the panel
			if(playerX < 10){
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		/*RESET:
		 * event to restart the game when ENTER is presses.
		 */
		if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(!play){
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				//add default position for paddle, playerX
				playerX = 310;
				score = 0;
				totalBricks = 14;
				mapper = new MapGenerator(2, 7);
				
				repaint();
			}
		}
	}
	
	public void moveRight(){
		play = true;
		playerX+=20;
	}
	
	public void moveLeft(){
		play = true;
		playerX-=20;
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	
}
