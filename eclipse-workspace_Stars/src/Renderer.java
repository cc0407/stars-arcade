import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Renderer extends JPanel {
	/**
	 * 
	 */
	public Main m;
	public Keybinds keybinds;
	private static final long serialVersionUID = 1L;
	private boolean showBoxes = false;
	public boolean progressFlash = false;
	public double progress;
	private int shipX;
	private int shipY;
	private FontMetrics fontMetrics;
	private BufferedImage meteorImg;
	private BufferedImage toolbarImg;
	private Missile mega;
	private JButton menu = new JButton("MAIN MENU");

	
	public Renderer(Main m, int width, int height) {
		this.m = m;
		this.setLayout(null);
		keybinds = new Keybinds(m);
		this.addKeyListener(keybinds);
		this.setFocusable(true);
		this.setBackground(Color.BLACK);
		this.setSize(width, height);
		this.setVisible(true);
		menu.setBounds(percentX(90) / 2, percentY(55), percentX(10), percentY(5));
		menu.setVisible(false);
		menu.addActionListener(m.events.restart);
		this.add(menu);
		
		try {
			meteorImg = ImageIO.read(new File("res\\Asteroid.png"));
			toolbarImg = ImageIO.read(new File("res\\toolbar.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		if(m.run) {
			if(menu.isVisible())
				menu.setVisible(false);
			
			super.paintComponent(g);
			
			//Background
			for (int i = 0; i < 1500; i++) {
				g.setColor(new Color(255, 255, 255, m.world.particles[i][5]));
				g.fillRect(m.world.particles[i][2], m.world.particles[i][3], m.world.particles[i][0],
						m.world.particles[i][1]);
			}
			
			
			//Missiles
			for (Missile m : m.ship.getMissiles()) {
				g.setColor(Color.RED);
				g.fillRect(m.getX(), m.getY(), m.hitbox.width, m.hitbox.height);
				
				if(showBoxes) {
					g.setColor(Color.BLUE);
					g.drawRect(m.hitbox.x, m.hitbox.y, m.hitbox.width, m.hitbox.height);
				}
			}
	
			//ship
			if(m.ship.alive)
			{
				g.drawImage(m.ship.getImage(), m.ship.getX(), m.ship.getY(), m.ship.getWidth(), m.ship.getHeight(), this);
	
			
				if(showBoxes){
					g.setColor(Color.BLUE);
					g.drawRect(m.ship.getX(), m.ship.getY(), m.ship.getWidth(), m.ship.getHeight());
				}
			}
			
			//Shield
			if(Skill.get("SHIELD").isActive())
			{
				g.setColor(new Color(0,0,255,100));
				g.fillOval(m.ship.getX( -percentY(2) ), m.ship.getY( -percentY(2) ), m.ship.getWidth( percentY(3.7) ), m.ship.getHeight( percentY(3.7) ));
			}
			
			//Mega
			if(Skill.get("MEGA").isActive())
			{

				int[] xComp = {m.ship.getX( percentX(5.25) ), m.ship.getX( percentX(3.4) ), m.ship.getX( percentX(3.4) ), m.ship.getX( percentX(5.25) )};
				int[] yComp = {m.ship.getY( percentY(26.5) ), m.ship.getY( percentY(3.15) ), m.ship.getY( percentY(2.8) ), m.ship.getY( percentY(20) )};
				g.setColor(Color.RED);
				g.fillPolygon(xComp, yComp, 4);
				try {
				mega = m.ship.getMega();
				g.fillRect(mega.getX(), mega.getY(), mega.getWidth(), mega.getHeight());
				}catch(NullPointerException ignored) {
					ignored.printStackTrace();
				}
				
			}
			

			
			//Meteors
			for (Meteor m : m.world.getMeteors()) {
				g.setColor(Color.WHITE);
				g.drawImage(meteorImg, m.getX(), m.getY(), m.hitbox.width, m.hitbox.height,this);
				
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
				g.drawImage(toolbarImg, 0, percentY(95) , this.getWidth(), percentY(5) , this);
				
		
				//Health Bar
				g.setColor(m.ship.getHealthColour());
				g.fillRect(percentX(0.25), percentY(96), (int) (percentX(22) * m.ship.healthAsPercent()), percentY(3.5));
				g.setColor(Color.BLACK);		
				g.drawRect(percentX(0.25), percentY(96), percentX(22), percentY(3.5));

				//Score
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, percentY(2.5)));
				g.setColor(Color.BLACK);
				fontMetrics = g.getFontMetrics();
				g.drawString(m.world.getScore() + "", percentX(30) - fontMetrics.stringWidth(m.world.getScore() + ""), percentY(98.5));

				
				//Skills on toolbar
				for(int i = 0; i < m.ship.skills.length; i++) {
					g.setColor(new Color(255,255,255,255));
					g.fillRect(percentX(38 + (7 * i)), percentY(95.5),percentY(4.25), percentY(4.25));
					g.drawImage(m.ship.skills[i].getImg(), percentX(38+ (7 * i)), percentY(95.5), percentY(4.25), percentY(4.25), this);
					if(m.ship.skills[i].isActive())
					{
						g.setColor(new Color(0,0,255,150));
						g.fillRect(percentX(38 + (7 * i)), percentY(95.5), percentY(4.25), (int) (percentY(4.25) * m.ship.skills[i].percentRemaining()));
					}
					else if(m.ship.skills[i].isOnCooldown()) {
						g.setColor(new Color(0,0,0,150));
						g.fillRect(percentX(38 + (7 * i)), percentY(95.5),percentY(4.25), percentY(4.25));
						g.setColor(Color.WHITE);
						g.fillArc(percentX(38 + (7 * i)) + percentY(0.5), percentY(96), percentY(3.25), percentY(3.25), 90, m.ship.skills[i].getArcCooldown());					
					}
				}
				
				
				
				//Progress Bar
				g.setColor(Color.DARK_GRAY);
				//horizontal
				g.fillRect(percentX(72), percentY(97.5), percentX(25), percentY(.25));
				//verticals
				g.fillRect(percentX(72), percentY(97), percentY(.25), percentY(1.25));
				g.fillRect(percentX(97), percentY(97), percentY(.25), percentY(1.25));
				
				
				//miniShip
				progress =(m.world.getScore() + 0.0) / m.world.firstBossScore;
				if(progress > 1)
					progress = 1;
				g.drawImage(m.ship.getImage(), (int) (percentX(72) + (percentX(24) * progress)),percentY(96.5),percentY(2.25),percentY(2.25),this);
				if(progress >= 1 && progressFlash)
				{
					g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, percentY(2.5)));
					g.setColor(Color.RED.darker());
					drawString("!", percentX(98), percentY(98.25), g);
				}
				
			}
			/////////////////////////////////////////////////////
			
			//Death Screen
			if(!m.ship.alive)
			{
				g.setColor(new Color(255,0,0,100));
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.WHITE);
				
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, percentY(7.5)));
				drawString("You Died", null, null, g);
				
				g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, percentY(2.5)));
				drawString("Press 'Space' to continue", null, percentY(50), g);
				
				menu.setVisible(true);
			}
			
			/////////////////////////////////////////////////////
			//Pause Screen
			if(m.f.paused)
			{
				g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, percentY(5)));
				g.setColor(new Color(0,0,0,150));
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.WHITE);
				drawString("PAUSED", null, percentY(45), g);
				menu.setVisible(true);
			}
		}
	}
	
	public int percentX(int percent) {
		return this.getWidth() * percent / 100;
	}
	
	public int percentY(int percent) {
		return this.getHeight() * percent / 100;
	}
	
	public int percentX(double percent) {
		double result = this.getWidth() * percent / 100.0;
		return (int) result;
	}
	
	public int percentY(double percent) {
		double result = this.getHeight() * percent / 100.0;
		return (int) result;
	}
	
	
	/**
	 * @param s - the string to be drawn
	 * @param x - the x coordinate, enter null for it to be horizontally centered
	 * @param y - the y coordinate, enter null for it to be vertically centered
	 * @param g - the Graphics component of the JPanel
	 */
	protected void drawString(String s, Integer x, Integer y, Graphics g) {
		if(x == null) {
			x = (this.getWidth() - g.getFontMetrics().stringWidth(s) ) / 2;
		}
		if(y == null) {
			y = (this.getHeight() - g.getFontMetrics().getFont().getSize()) /  2;
		}
		g.drawString(s, x, y);
	}
}
