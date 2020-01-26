import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Ship {


	public boolean isFiring = false;
	public boolean alive = true;
	public Skill shield = Skill.SHIELD;
	public Skill multi = Skill.MULTI;
	public Skill boost = Skill.BOOST;
	public Mega mega;
	public int dy;
	public int dx;
	public int health = 100;
	public int maxHealth = 100;
	public int speed;
	public int currentSpeed;
	public BufferedImage image;
	public Rectangle hitbox;
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
		mega = new Mega();
		startingAnim();
	}
	public double healthAsPercent() {
		return (health + 0.0) / (maxHealth + 0.0);
	}
	public void stopPressingKeys() {
		this.dx = 0;
		this.dy = 0;
		this.isFiring = false;
	}
	
	public void resurrect() {
		alive = true;
		health = 100;
		hitbox.x = m.f.WIDTH;
		hitbox.y = m.f.HEIGHT/2;
		m.t.healthShow = false;
		m.world.reset();
		shield.reset();
		multi.reset();
		boost.reset();
		mega.reset();
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

		
		missiles.add(new Missile(hitbox.x + hitbox.width, hitbox.y + (hitbox.height - missileHeight)/ 2, missileWidth, missileHeight, missileSpeed, true));
		
		if(multi.isActive()) {
			missiles.add(new Missile(hitbox.x + (hitbox.width * 6 / 10), hitbox.y + (hitbox.height * 1/10), missileWidth, missileHeight, missileSpeed, false));
			missiles.add(new Missile(hitbox.x + (hitbox.width * 6 / 10), hitbox.y + (hitbox.height * 9/10), missileWidth, missileHeight, missileSpeed, false));
		}
	}

	public void move() {
		hitbox.x += dx;
		hitbox.y += dy;
		checkOOB();
	}

	private void checkOOB() {
		if (hitbox.x < 0)
			hitbox.x = 0;
		//checks if right edge of ship is touching right edge of screen
		else if (hitbox.x + hitbox.width >= m.f.WIDTH )
			hitbox.x = m.f.WIDTH - hitbox.width;
		
		if (hitbox.y <  m.f.jp.percentY(1))
			hitbox.y =  m.f.jp.percentY(1);
		
		//checks if bottom edge of ship is touching the toolbar
		else if (hitbox.y + hitbox.height >= m.f.jp.percentY(94))
			hitbox.y = m.f.jp.percentY(94) - hitbox.height;
	}
	
	public void increaseSpeed() {
		this.currentSpeed *= 1.5;
	}
	
	public void revertSpeed() {
		this.currentSpeed = speed;
	}

	public int getShipX() {
		return hitbox.x;
	}

	public int getShipY() {
		return hitbox.y;
	}

	public BufferedImage getImage() {
		return image;
	}

	public ArrayList<Missile> getMissiles() {
		return missiles;
	}
	
	public void updateMissiles() {
		for (int i = 0; i < this.missiles.size(); i++) 
		{
			Missile missile = this.missiles.get(i);

			if (missile.getX() < m.f.WIDTH)
				missile.move();
			else {
				this.missiles.get(i).stopSound();
				this.missiles.remove(i);
			}
		}
	}
	
	
	class Mega extends Skill {
		public Rectangle hitbox;
		private final int cooldown = 3200;
		private final int duration = 300;
		
		//w=1920,500
		public Mega() {
			super(300, 3200, 0, "res\\mega.wav");
//			hitbox = new Rectangle(m.f.WIDTH , m.f.HEIGHT / 2);
			hitbox = new Rectangle(1920 ,500);
			updateHitbox();

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
		public boolean intersects(Rectangle rect) {
			return this.hitbox.intersects(rect);

		}
		
		@Override
		public int timeLeft() {
			if(!this.isActive) {
				return -1;
			}
			if(ticksLeft <= 0) {
				ticksLeft = duration;
				currentCooldown = cooldown;
				isActive = false;
				return 0;
			}
			this.ticksLeft --;
			updateHitbox();
			return ticksLeft;
		}
		public void updateHitbox() {
					hitbox.x = getShipX() + 100;
					hitbox.y = getShipY() - 218;
		}
	}
}


