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
	public int speed = 10;
	public BufferedImage image;
	public Rectangle hitbox = new Rectangle (64, 64);
	private ArrayList<Missile> missiles;

	
	public Main m;
	
	public Ship(Main m) {
		this.m = m;
		mega = new Mega();
		
		missiles = new ArrayList<Missile>();
		try {
			image = ImageIO.read(new File("res\\Spaceship.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		hitbox.x = m.f.WIDTH;
		hitbox.y = m.f.HEIGHT/2;
		startingAnim();
	}
	
	
	public void resurrect() {
		alive = true;
		health = 100;
		hitbox.x = m.f.WIDTH;
		hitbox.y = m.f.HEIGHT/2;
		m.t.healthShow = false;
		m.world.setScore(0);
		m.world.spawnAmt = 2;
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
		missiles.add(new Missile(hitbox.x, hitbox.y,50,5));
		
		if(multi.isActive()) {
			missiles.add(new Missile(hitbox.x - 25, hitbox.y + 27,50,5));
			missiles.add(new Missile(hitbox.x - 25, hitbox.y - 27,50,5));
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
		else if (hitbox.x > m.f.WIDTH-80)
			hitbox.x = m.f.WIDTH-80;
		
		if (hitbox.y < 10)
			hitbox.y = 10;
		else if (hitbox.y > m.f.HEIGHT - 170)
			hitbox.y = m.f.HEIGHT - 170;
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
			else
				this.missiles.remove(i);
		}
	}
	
	
	class Mega extends Skill {
		public Rectangle hitbox;
		private final int cooldown = 3200;
		private final int duration = 300;
		
		//w=1920,500
		public Mega() {
			super(300, 3200, "res\\mega.wav");
			//TODO program doesnt finish running implementation of f when this is called, leading to nullPoint
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
			if(this.hitbox.intersects(rect)) 
				return true;
			else 
				return false;
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


