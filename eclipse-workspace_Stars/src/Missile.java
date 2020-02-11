import java.awt.Rectangle;

enum type{
	missile,
	mega,
	mine;
}
public class Missile {
	private final int MISSILE_SPEED;
	private final boolean hasSound;
	public Rectangle hitbox;
	private Ship s = null;
	private type t;
	
	//w=50, h =5
	public Missile(int x, int y, int width, int height, int speed, boolean hasSound, type t) {
		this(null, x, y, width, height, speed, hasSound, t);
	}
	
	public Missile(Ship s, int x, int y, int width, int height, int speed, boolean hasSound, type t) {
		this.s = s;
		this.t = t;
		hitbox = new Rectangle(x, y, width , height);
		MISSILE_SPEED = speed;
		this.hasSound = hasSound;
		if(hasSound)
			startSound();   
	}

	public void move() {
		//if ship is null then move right otherwise follow ship
		try {
			hitbox.x = this.s.getX() + 100;
			hitbox.y = this.s.getY() - 218;
		} catch(NullPointerException e) {
			hitbox.x += MISSILE_SPEED;
		}
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
	
	public type getType() {
		return this.t;
	}
	
	public void pauseSound() {
		if(this.hasSound) {
			Sound.get("missile").pause();
		}
	}

	public void startSound() {
		if(this.hasSound) {
			Sound.get("missile").addToLoop();
		}
	}
	public void stopSound() {
		if(this.hasSound) {
			Sound.get("missile").stop();
		}
	
	}

	public boolean isFollowing() {
		return this.s != null;
	}
}
