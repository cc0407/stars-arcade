import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
enum Direction{
	LEFT,
	RIGHT,
	UP,
	DOWN;
}
public class Ship {


	public boolean isFiring = false;
	public boolean alive = true;
	public Skill[] skills = {Skill.get("SHIELD"), Skill.get("MULTI"), Skill.get("BOOST"), Skill.get("MEGA")};
	private int dy;
	private int dx;
	private int health = 100;
	private int maxHealth = 100;
	private int speed;
	private int currentSpeed;
	private BufferedImage image;
	private Rectangle hitbox;
	private ArrayList<Missile> missiles;
	int missileHeight;
	int missileWidth;
	int missileSpeed;

	
	public Main m;
	
	public Ship(Main m) {
		this.m = m;
		
		
		missiles = new ArrayList<Missile>();
		
		missileHeight = m.f.jp.percentY(0.5);
		missileWidth = m.f.jp.percentY(4.5);
		missileSpeed = m.f.jp.percentY(1.4);
		this.speed = m.f.jp.percentY(1);
		this.currentSpeed = this.speed;
		
		int width = m.f.jp.percentY(6);
		int height = m.f.jp.percentY(6);
		hitbox = new Rectangle(m.f.WIDTH - width, (m.f.HEIGHT - height)/2, width, height);
		try {
			image = ImageIO.read(new File("res\\Spaceship.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Use this to increase or decrease health
	public void changeHealth(int amt) {
		this.health += amt;
		if(this.health <= 0) {
			this.die();
		}
	}
	
	public Color getHealthColour() {
		double percentHealth = healthAsPercent();
		Color c = Color.RED;
		
		if(percentHealth > 0.61) {
			c = Color.GREEN;
		}
		else if(percentHealth > 0.21)
			c = Color.YELLOW;
		
		return c;
	}
	
	public double healthAsPercent() {
		return (health + 0.0) / (maxHealth + 0.0);
	}
	
	public void stopPressingKeys() {
		this.dx = 0;
		this.dy = 0;
		this.isFiring = false;
	}
	
	public void setDirection(Direction d) {
		switch(d) {
			case LEFT:
				this.dx = -this.currentSpeed;
				break;
			
			case RIGHT:
				this.dx = this.currentSpeed;
				break;
			
			case UP:
				this.dy = -this.currentSpeed;
				break;
				
			case DOWN:
				this.dy = this.currentSpeed;
				break;
		}
				
	}
	
	public void stopDirection(Direction d) {
		switch(d) {
			case LEFT:
			
			case RIGHT:
				this.dx = 0;
				break;
			
			case UP:
				
			case DOWN:
				this.dy = 0;
				break;
		}
				
	}
	
	public void resurrect() {
		alive = true;
		health = 100;
		hitbox.x = m.f.WIDTH - getWidth();
		hitbox.y = (m.f.HEIGHT - getHeight())/2;
		m.t.healthShow = false;
		m.world.reset();

		for(Skill s : this.skills) {
			s.reset();
		}
		startingAnim();
		
	}
	public void die() {
		alive = false;
		m.world.clearMeteors();
	}

	public void startingAnim() {
		dx = -15;
	}
	
	public void fire() {

		//sets x value at right side of ship, centers y value on nose of ship, width of missile, height of missile, speed of missile, plays sound true
		missiles.add(new Missile(getX() + getWidth(), getY() + (getHeight() - missileHeight)/ 2, missileWidth, missileHeight, missileSpeed, true));
		
		if(Skill.get("MULTI").isActive()) {
			//sets x value in front of wing guns, sets y value at end of wing guns, width of missile, height of missile, speed of missile, plays sound false
			missiles.add(new Missile(getX() + (getWidth() * 6 / 10), getY() + (getHeight() * 1/10), missileWidth, missileHeight, missileSpeed, false));
			missiles.add(new Missile(getX() + (getWidth() * 6 / 10), getY() + (getHeight() * 9/10), missileWidth, missileHeight, missileSpeed, false));
		}
	}
	
	//for use with 'skill' missiles ie mega
	public void fire(int width, int height, int speed, boolean follows) {
		if(follows)
			missiles.add(new Missile(this, getX() + getWidth(), getY() + (getHeight() - missileHeight)/ 2, width, height, speed, false));
		else
			missiles.add(new Missile(getX() + getWidth(), getY() + (getHeight() - missileHeight)/ 2, width, height, speed, false));
	}

	public void move() {
		if(getX() + dx < m.f.WIDTH - getWidth() && getX() + dx >= 0)
			hitbox.x += dx;
		
		if(getY() + dy + getHeight() < m.f.jp.percentY(94) && getY() + dy >= 0)
			hitbox.y += dy;
	}
	
	public void increaseSpeed(double percentAsDecimal) {
		this.currentSpeed *= percentAsDecimal;
	}
	
	public void revertSpeed() {
		this.currentSpeed = speed;
	}

	public int getX() {
		return hitbox.x;
	}

	public int getY() {
		return hitbox.y;
	}
	
	public int getWidth() {
		return hitbox.width;
	}
	
	public int getHeight() {
		return hitbox.height;
	}
	
	public boolean intersects(Rectangle r) {
		return this.hitbox.intersects(r);
	}

	public BufferedImage getImage() {
		return image;
	}

	public ArrayList<Missile> getMissiles() {
		return new ArrayList<>(missiles);
	}
	
	public void removeMissile(Missile m) {
		int index = this.missiles.indexOf(m);
		this.missiles.get(index).stopSound();
		this.missiles.remove(m);
	}
	
	public void updateMissiles() {
		
		for(Missile missile : getMissiles()) {
			if (missile.getX() <= m.f.WIDTH)
				missile.move();
			else {
				this.removeMissile(missile);
			}
		}

	}
	
	public void advanceSkills() {
		for(int i = 0; i < skills.length; i++) {
			if(skills[i].decreaseTick() == 0) {
				if(skills[i].getPath().contains("boost"))
					revertSpeed();
				if(skills[i].getPath().contains("mega"))
					removeMega();
			}
		}
	}
	
	public void swapSkill(String key, int slot) {
		for(Skill s : this.skills) {
			if(s.getName().equalsIgnoreCase(key)) {
				return;
			}
		}
		skills[slot] = Skill.get(key);
		m.f.ep.nextEquip();
	}
	
	//THIS IS WHERE THE SKILL ACTUALLY DOES SOMETHING TO THE GAME
	public void startSkill(int index) {
		if(skills[index].start()) {
			
			String name = skills[index].getName();
			if(name.equals("mega"))
				fire(1920, 500, 0, true);
			else if(name.equals("boost"))
				increaseSpeed(1.5);
			else if(name.equals("mine"))
				fire(200, 200, 0, false);
		}
	}
	
	public Missile getMega() {
		for(Missile missile : getMissiles()) {
			if(missile.isFollowing()) {
				return missile;
			}
		}
		return null;
	}
	
	public void removeMega() {
		for(Missile missile : getMissiles()) {
			if(missile.isFollowing()) {
				this.removeMissile(missile);
			}
		}
	}
}


