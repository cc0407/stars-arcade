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
	private BufferedImage shieldImg;
	private BufferedImage multiImg;
	private BufferedImage boostImg;
	private BufferedImage meteorImg;
	private BufferedImage toolbarImg;
	//private int[] xComp = {m.ship.hitbox.x + 100,m.ship.hitbox.x -27, m.ship.hitbox.x + 50, m.ship.hitbox.x, m.ship.hitbox.x + 50,  m.ship.hitbox.x - 27,  m.ship.hitbox.x +100,  m.ship.hitbox.x + 100},
	//		yComp = {m.ship.hitbox.y +280,  m.ship.hitbox.y - 27,  m.ship.hitbox.y - 13,  m.ship.hitbox.y,  m.ship.hitbox.y + 13,  m.ship.hitbox.y + 27,  m.ship.hitbox.y - 220,  m.ship.hitbox.y + 280};
	
	
	
	public Renderer(Main m) {
		this.m = m;
		try {
			shieldImg = ImageIO.read(new File("res\\Shield.png"));
			multiImg = ImageIO.read(new File("res\\multi.png"));
			boostImg = ImageIO.read(new File("res\\boost.png"));
			meteorImg = ImageIO.read(new File("res\\Asteroid.png"));
			toolbarImg = ImageIO.read(new File("res\\toolbar.png"));
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
				g.drawImage(toolbarImg, 0, percentY(95) , m.f.WIDTH, percentY(5) , this);

				
		
				//Health Bar
				if(m.ship.health>56)
					g.setColor(new 	Color(0	,255,0, 255));
				else if(m.ship.health >21)
					g.setColor(new 	Color(255,255,0, 255));
				else
					g.setColor(new 	Color(255, 0,0, 255));
				g.fillRect(percentX(0.25), percentY(96), m.ship.health*percentX(0.25), percentY(3.5));
				g.setColor(Color.BLACK);		
				g.drawRect(percentX(0.25), percentY(96), m.ship.health*percentX(0.25), percentY(3.5));

				//Score
				g.setFont(new Font("Monospaced", Font.BOLD, 30));
				g.setColor(Color.BLACK);
				fontMetrics = g.getFontMetrics();
				g.drawString(m.world.getScore() + "", percentX(30) - fontMetrics.stringWidth(m.world.getScore() + ""), percentY(98.5));

				//TODO, P1 DONE DO THE REST
				////////////
				//pUP1
				g.setColor(new Color(255,255,255,255));
				g.fillRect(percentX(38), percentY(95.5),percentY(4.25), percentY(4.25));
				g.drawImage(shieldImg, percentX(38), percentY(95.5), percentY(4.25), percentY(4.25), this);
				//pUP1ACTIVE
				if(m.ship.shield.isActive())
				{
					g.setColor(new Color(0,0,255,150));
					g.fillRect(percentX(38), percentY(95.5), percentY(4.25), (int) (percentY(4.25) * m.ship.shield.percentRemaining()));
				}
				else if(m.ship.shield.isOnCooldown()) {
					g.setColor(new Color(0,0,0,150));
					g.fillRect(percentX(38), percentY(95.5),percentY(4.25), percentY(4.25));
					g.setColor(Color.WHITE);
					//g.drawOval(700, 1007, 30, 30);
					g.fillArc(percentX(38) + percentY(0.5), percentY(96), percentY(3.25), percentY(3.25), 90, m.ship.shield.getCurrentCooldown()*6/10);					
				}
				////////////
				//pUP2
				g.setColor(new Color(255,255,255,255));
				g.setColor(Color.GRAY);
				g.drawImage(multiImg, percentX(45),percentY(95.5), percentY(4.25), percentY(4.25), this);
				
				//pUP2ACTIVE
				if(m.ship.multi.isActive())
				{
					g.setColor(new Color(0,0,255,150));
					g.fillRect(percentX(45), percentY(95.5), percentY(4.25), (int) (percentY(4.25) * m.ship.multi.percentRemaining()));
				}else if(m.ship.multi.isOnCooldown()) {
					g.setColor(new Color(0,0,0,150));
					g.fillRect(percentX(45), percentY(95.5),percentY(4.25), percentY(4.25));
					g.setColor(Color.WHITE);
					g.fillArc(percentX(45) + percentY(0.5), percentY(96),percentY(3.25), percentY(3.25), 90, m.ship.multi.getCurrentCooldown()*3/10);
					
					
				}
				////////////
				//pUP3
				g.setColor(new Color(255,255,255,255));
				g.setColor(Color.GRAY);
				g.drawImage(boostImg, percentX(52),percentY(95.5), percentY(4.25), percentY(4.25), this);
				
				//pUP3ACTIVE
				if(m.ship.boost.isActive())
				{
					g.setColor(new Color(0,0,255,150));
					g.fillRect(percentX(52), percentY(95.5), percentY(4.25), (int) (percentY(4.25) * m.ship.boost.percentRemaining()));
				} else if(m.ship.boost.isOnCooldown()) {
					g.setColor(new Color(0,0,0,150));
					g.fillRect(percentX(52),percentY(95.5), percentY(4.25), percentY(4.25));
					g.setColor(Color.WHITE);
					g.fillArc(percentX(52) + percentY(0.5),percentY(96), percentY(3.25), percentY(3.25), 90, m.ship.boost.getCurrentCooldown()*3/10);
				}
				
				
				////////////
				//pUP4
				g.setColor(Color.GRAY);
				g.fillRect(percentX(59),percentY(95.5), percentY(4.25), percentY(4.25));
				
				//pUP4ACTIVE
				if(m.ship.mega.isActive()){
					g.setColor(new Color(0,0,255,150));
					g.fillRect(percentX(59), percentY(95.5), percentY(4.25), (int) (percentY(4.25) * m.ship.boost.percentRemaining()));
				} else if(m.ship.mega.isOnCooldown()) {
					g.setColor(new Color(0,0,0,150));
					g.fillRect(percentX(59),percentY(95.5), percentY(4.25), percentY(4.25));
					g.setColor(Color.WHITE);
					g.fillArc(percentX(59) + percentY(0.5),percentY(96), percentY(3.25), percentY(3.25), 90, m.ship.mega.getCurrentCooldown()*3/10);
				}
				////////////
				//vert Line 3
				
				
				//Progress Bar
				g.setColor(Color.DARK_GRAY);
				//horizontal
				g.fillRect(percentX(72), percentY(97.5), percentX(25), percentY(.25));
				//verticals
				g.fillRect(percentX(72), percentY(97), percentY(.25), percentY(1.25));
				g.fillRect(percentX(97), percentY(97), percentY(.25), percentY(1.25));
				
				
				//miniShip
				progress = m.world.getScore() * 5/100;
				if(progress > 500)
					progress = 500;
				g.drawImage(m.ship.image, percentX(72),percentY(96.5),23,23,this);
				
//				if(progress == 500 && progressFlash)
//				{
					g.setFont(new Font("Monospaced", Font.BOLD, 30));
					g.setColor(Color.RED.darker());
					g.drawString("!", percentX(98), percentY(98.25));
//				}
				
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
	
	public int percentX(int percent) {
		return m.f.WIDTH * percent / 100;
	}
	
	public int percentY(int percent) {
		return m.f.HEIGHT * percent / 100;
	}
	
	public int percentX(double percent) {
		double result = m.f.WIDTH * percent / 100.0;
		return (int) result;
	}
	
	public int percentY(double percent) {
		double result = m.f.HEIGHT * percent / 100.0;
		return (int) result;
	}
}
