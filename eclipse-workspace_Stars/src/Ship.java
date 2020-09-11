import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	public Skill[] skills = new Skill[4];
	private int dy;
	private int dx;
	private int health = 100;
	private int maxHealth = 100;
	private int speed;
	private int currentSpeed;
	private int mineSize;
	private BufferedImage image;
	private Rectangle hitbox;
	private ArrayList<Missile> missiles;
	int missileHeight;
	int missileWidth;
	int missileSpeed;

	
	public Main m;
	
	public Ship(Main m) {
		this.m = m;
		
		initSkillsFromFile();
		
		missiles = new ArrayList<Missile>();
		
		missileHeight = m.f.jp.percentY(0.5);
		missileWidth = m.f.jp.percentY(4.5);
		missileSpeed = m.f.jp.percentY(1.4);
		mineSize = m.f.jp.percentY(18.5);
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
		missiles.add(new Missile(getX() + getWidth(), getY() + (getHeight() - missileHeight)/ 2, missileWidth, missileHeight, missileSpeed, true, type.missile));
		
		if(Skill.get("MULTI").isActive()) {
			//sets x value in front of wing guns, sets y value at end of wing guns, width of missile, height of missile, speed of missile, plays sound false
			missiles.add(new Missile(getX() + (getWidth() * 6 / 10), getY() + (getHeight() * 1/10), missileWidth, missileHeight, missileSpeed, false, type.missile));
			missiles.add(new Missile(getX() + (getWidth() * 6 / 10), getY() + (getHeight() * 9/10), missileWidth, missileHeight, missileSpeed, false, type.missile));
		}
	}
	
	//for use with 'skill' missiles ie mega
	public void fire(int width, int height, int speed, boolean follows) {
		if(follows)
			missiles.add(new Missile(this, getX() + getWidth(), getY() + (getHeight() - missileHeight)/ 2, width, height, speed, false, type.mega));
		else
			missiles.add(new Missile(getX() + getWidth(), getY() + (getHeight() - missileHeight)/ 2, width, height, speed, false, type.mine));
	}
	
	//for recreating missiles
	public void fire(int width, int height, int x, int y, int speed, boolean follows) {
		if(follows)
			missiles.add(new Missile(this, x, y, width, height, speed, false, type.mega));
		else
			missiles.add(new Missile(x, y, width, height, speed, false, type.mine));
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
	public int getX(int offset) {
		return hitbox.x + offset;
	}

	public int getY() {
		return hitbox.y;
	}
	public int getY(int offset) {
		return hitbox.y + offset;
	}
	
	public int getWidth() {
		return hitbox.width;
	}
	public int getWidth(int offset) {
		return hitbox.width + offset;
	}
	
	public int getHeight() {
		return hitbox.height;
	}
	public int getHeight(int offset) {
		return hitbox.height + offset;
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
//		this.missiles.get(index).stopSound();
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
	
	public void initSkillsFromFile() {
		File file = new File("res\\skills.txt"); 
				  
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));
			String name; 
			int count = 0;
			while (((name = br.readLine()) != null) && count <= 3) {
				this.skills[count] = Skill.get(name);
				count++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
			  

	}
	
	public void saveSkillsToFile() {
		File file = new File("res\\skills.txt"); 
				  
		try {
            FileWriter writer = new FileWriter(file, false);
            for(Skill s : this.skills) {
            	writer.write(s.getName().toUpperCase() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
	
	public int swapSkill(String key, int slot) {
		int count = 0;
		for(Skill s : this.skills) {
			if(s.getName().equalsIgnoreCase(key)) {
				if(count == slot) {
					return -1;
				}
				else {
					switchSkillWithOther(key, slot, count);
				}
			}
			count++;
		}
		skills[slot] = Skill.get(key);
		return 1;
	}
	
	public void switchSkillWithOther(String key, int slot, int slot2) {
		Skill tempSkill = skills[slot2];
		
		skills[slot2] = skills[slot];
		skills[slot] = tempSkill;
		
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
				fire(mineSize, mineSize, 0, false);
		}
	}
	
	public Missile getMega() {
		for(Missile missile : getMissiles()) {
			if(missile.getType().equals(type.mega)) {
				return missile;
			}
		}
		return null;
	}
	
	public void removeMega() {
		for(Missile missile : getMissiles()) {
			if(missile.getType().equals(type.mega)) {
				this.removeMissile(missile);
			}
		}
	}
	
	public void newMine(int width, int height, int x, int y) {
		if(width < this.mineSize / 4)
			return;
		else {
			fire(width, height, x, y, 0, false);
		}
	}
	
	public void printSkills() {
		for(Skill s : this.skills) {
			System.out.print(s.getName());
		}
	}
}


