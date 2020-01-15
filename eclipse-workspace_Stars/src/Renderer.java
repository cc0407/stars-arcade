import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Renderer extends JPanel {
	/**
	 * 
	 */
	public Main m;
	private static final long serialVersionUID = 1L;
	private boolean showBoxes = false;
	public boolean progressFlash = false;
	public int progress, shipX, shipY;
	private FontMetrics fontMetrics;
	private BufferedImage shieldImg, multiImg, boostImg, meteorImg;
	//private int[] xComp = {m.ship.hitbox.x + 100,m.ship.hitbox.x -27, m.ship.hitbox.x + 50, m.ship.hitbox.x, m.ship.hitbox.x + 50,  m.ship.hitbox.x - 27,  m.ship.hitbox.x +100,  m.ship.hitbox.x + 100},
	//		yComp = {m.ship.hitbox.y +280,  m.ship.hitbox.y - 27,  m.ship.hitbox.y - 13,  m.ship.hitbox.y,  m.ship.hitbox.y + 13,  m.ship.hitbox.y + 27,  m.ship.hitbox.y - 220,  m.ship.hitbox.y + 280};
	
	
	public Renderer(Main m) {
		this.m = m;
		try {
			shieldImg = ImageIO.read(new File("res\\Shield.png"));
			meteorImg = ImageIO.read(new File("res\\Asteroid.png"));
			//boostImg = ImageIO.read(new File("res\\boost.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		if(m.run) {
			super.paintComponent(g);
			
			//Background
			for (int i = 0; i < 1500; i++) {
				g.setColor(new Color(255, 255, 255, m.world.particles[i][5]));
				g.fillRect(m.world.particles[i][2], m.world.particles[i][3], m.world.particles[i][0],
						m.world.particles[i][1]);
			}
	
			//ship
			if(m.ship.alive)
			{
				g.drawImage(m.ship.image, m.ship.hitbox.x, m.ship.hitbox.y, this);
	
			
				if(showBoxes){
					g.setColor(Color.BLUE);
					g.drawRect(m.ship.hitbox.x, m.ship.hitbox.y, m.ship.hitbox.width, m.ship.hitbox.height);
				}
			}
			
			//Shield
			if(m.ship.shield.isActive())
			{
				g.setColor(new Color(0,0,255,100));
				g.fillOval(m.ship.hitbox.x - 24, m.ship.hitbox.y - 20, m.ship.hitbox.width + 40, m.ship.hitbox.height + 40);
			}
			
			//mega
			if(m.ship.mega.isActive())
			{
				shipX = m.ship.getShipX();
				shipY = m.ship.getShipY();
				int[] xComp = {shipX + 100, shipX + 50, shipX + 50, shipX + 100};
				int[] yComp = {shipY + 286, shipY + 34, shipY + 30, shipY - 218,};
				g.setColor(Color.RED);
				g.fillPolygon(xComp, yComp, 4);
				g.fillRect(m.ship.mega.getX(), m.ship.mega.getY(), m.ship.mega.getWidth(), m.ship.mega.getHeight());
				
			}
			
			
			//Missiles
			ArrayList<Missile> ms = m.ship.getMissiles();
			for (Object m1 : ms) {
				Missile m = (Missile) m1;
				g.setColor(Color.RED);
				g.fillRect(m.getX(), m.getY(), m.hitbox.width, 5);
				
				if(showBoxes) {
				g.setColor(Color.BLUE);
				g.drawRect(m.hitbox.x, m.hitbox.y, 50, 5);
				}
			}
			
			//Meteors
			ArrayList<Meteor> mt = m.world.getMeteors();
			for (Object m2 : mt) {
				Meteor m = (Meteor) m2;
				g.setColor(Color.WHITE);
				g.drawImage(meteorImg, m.getX(), m.getY(), m.hitbox.width, m.hitbox.height,this);
				//g.fillRect(m.getX(), m.getY(), m.hitbox.width, m.hitbox.height);
				
				if(showBoxes) {
				g.setColor(Color.BLUE);
				g.drawRect(m.hitbox.x, m.hitbox.y, m.hitbox.width, m.hitbox.height);
				}
			}
			
			/////////////////////////////////////////////////////
			//Health and Panel
			if(m.t.healthShow == true)
			{
	
				//Panel
				g.setColor(new Color(180,180,180,255));
				g.fillRect(0,990,m.f.WIDTH, 90);
				
				//vert Line 1
				g.setColor(Color.DARK_GRAY);
				g.fillRect(420,993,10, 58);
				
		
				//Health Bar
				if(m.ship.health>56)
					g.setColor(new 	Color(0	,255,0, 255));
				else if(m.ship.health >21)
					g.setColor(new 	Color(255,255,0, 255));
				else
					g.setColor(new 	Color(255, 0,0, 255));
				g.fillRect(10, 997, m.ship.health*4, 50);
				
				//Health Bar Outline
				g.setColor(new Color(255,255,255,255));		
				g.drawRect(10, 997, 399, 49);		
				
				//Score
				g.setFont(new Font("Monospaced", Font.BOLD, 30));
				fontMetrics = g.getFontMetrics();
				g.drawString(m.world.getScore() + "", 550 - fontMetrics.stringWidth(m.world.getScore() + ""), 1030);
				
				//vert Line 2
				g.setColor(Color.DARK_GRAY);
				g.fillRect(560,993,10, 58);
				
				////////////
				//pUP1
				g.setColor(new Color(255,255,255,255));
				g.fillRect(652,997,50, 50);
				g.drawImage(shieldImg, 652, 997, 50, 50, this);
				
				//pUP1ACTIVE
				if(m.ship.shield.isActive())
				{
					g.setColor(new Color(0,0,255,150));
					g.fillRect(652, 1047 - (m.ship.shield.ticksRemaining() * 5 / 30), 50, 50 - ((300 - m.ship.shield.ticksRemaining()) * 5 / 30));
				}
				else if(m.ship.shield.isOnCooldown()) {
					g.setColor(new Color(0,0,0,150));
					g.fillRect(652,997,50, 50);
					g.setColor(Color.WHITE);
					//g.drawOval(700, 1007, 30, 30);
					g.fillArc(662, 1007, 30, 30, 90, m.ship.shield.getCurrentCooldown()*6/10);					
				}
				////////////
				//pUP2
				g.setColor(new Color(255,255,255,255));
				g.fillRect(794,997,50, 50);
				
				//pUP2ACTIVE
				if(m.ship.multi.isActive())
				{
					g.setColor(new Color(0,0,255,150));
					g.fillRect(794, 1047 - (m.ship.multi.ticksRemaining() * 5 / 60), 50, 25 - ((300 - m.ship.multi.ticksRemaining()) * 5 / 60));
				}else if(m.ship.multi.isOnCooldown()) {
					g.setColor(new Color(0,0,0,150));
					g.fillRect(794,997,50, 50);
					g.setColor(Color.WHITE);
					g.fillArc(804, 1007, 30, 30, 90, m.ship.multi.getCurrentCooldown()*3/10);
					
					
				}
				////////////
				//pUP3
				g.setColor(new Color(255,255,255,255));
				g.fillRect(936,997,50, 50);
				
				//pUP3ACTIVE
				if(m.ship.boost.isActive())
				{
					g.setColor(new Color(0,0,255,150));
					g.fillRect(936, 1047 - (m.ship.boost.ticksRemaining() * 5 / 60), 50, 25 - ((300 - m.ship.boost.ticksRemaining()) * 5 / 60));
				} else if(m.ship.boost.isOnCooldown()) {
					g.setColor(new Color(0,0,0,150));
					g.fillRect(936,997,50, 50);
					g.setColor(Color.WHITE);
					g.fillArc(946, 1007, 30, 30, 90, m.ship.boost.getCurrentCooldown()*3/10);
				}
				
				
				////////////
				//pUP4
				g.setColor(new Color(255,255,255,255));
				g.fillRect(1078,997,50, 50);
				
				//pUP4ACTIVE
				if(m.ship.mega.isActive()){
					g.setColor(new Color(0,0,255,150));
					g.fillRect(1078, 1047 - (m.ship.mega.ticksRemaining() * 5 / 30), 50, 50 - ((300 - m.ship.mega.ticksRemaining()) * 5 / 30));
				} else if(m.ship.mega.isOnCooldown()) {
					g.setColor(new Color(0,0,0,150));
					g.fillRect(1078,997,50, 50);
					g.setColor(Color.WHITE);
					g.fillArc(1088, 1007, 30, 30, 90, m.ship.mega.getCurrentCooldown()*9/80);
				}
				////////////
				//vert Line 3
				g.setColor(new Color(80,80,80,255));
				g.fillRect(1230,993,10, 58);
				
				
				//Progress Bar
				g.setColor(Color.DARK_GRAY);
				//horizontal
				g.fillRect(1330, 1021, 500, 3);
				//verticals
				g.fillRect(1327, 1011, 3, 23);
				g.fillRect(1830, 1011, 3, 23);
				
				
				//miniShip
				progress = m.world.getScore() * 5/100;
				if(progress > 500)
					progress = 500;
				g.drawImage(m.ship.image, 1327 + progress,1011 ,23,23,this);
				
				if(progress == 500 && progressFlash)
				{
					g.setFont(new Font("Monospaced", Font.BOLD, 30));
					g.setColor(Color.RED.darker());
					g.drawString("!", 1867, 1030);
				}
				
			}
			/////////////////////////////////////////////////////
			
			//Death Screen
			if(!m.ship.alive)
			{
				g.setColor(new Color(255,0,0,100));
				g.fillRect(0, 0, m.f.WIDTH, m.f.HEIGHT);
				
				g.setColor(Color.WHITE);
				g.setFont(new Font("Monospaced", Font.BOLD, 80));
				fontMetrics = g.getFontMetrics();
				g.drawString("You Died", (m.f.WIDTH - fontMetrics.stringWidth("You Died"))/2, (m.f.HEIGHT - 80)/2);
				
				g.setFont(new Font("Monospaced", Font.PLAIN, 30));
				fontMetrics = g.getFontMetrics();
				g.drawString("Press 'Space' to continue", (m.f.WIDTH - fontMetrics.stringWidth("Press 'Space' to continue"))/2 , (m.f.HEIGHT - 30)/2 + 30);
			}
			
			/////////////////////////////////////////////////////
			//Pause Screen
			if(m.f.paused)
			{
				g.setFont(new Font("Monospaced", Font.BOLD, 60));
				fontMetrics = g.getFontMetrics();
				g.setColor(new Color(0,0,0,150));
				g.fillRect(0, 0, m.f.WIDTH, m.f.HEIGHT);
				g.setColor(Color.WHITE);
				g.drawString("PAUSED", (m.f.WIDTH - fontMetrics.stringWidth("PAUSED"))/2, (m.f.HEIGHT - 60)/2);
			}
		}
	}
}
