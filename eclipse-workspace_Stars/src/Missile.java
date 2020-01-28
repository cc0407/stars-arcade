import java.awt.Rectangle;

public class Missile {
	private Sound missileSound = new Sound("res\\pew2.wav");
	private final int MISSILE_SPEED;
	private final boolean hasSound;
	public Rectangle hitbox;
	private Ship s = null;
	
	//w=50, h =5
	public Missile(int x, int y, int width, int height, int speed, boolean hasSound) {
		this(null, x, y, width, height, speed, hasSound);
	}
	
	public Missile(Ship s, int x, int y, int width, int height, int speed, boolean hasSound) {
		this.s = s;
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
	
	public void pauseSound() {
		if(this.hasSound)
			missileSound.pause();
	}

	public void startSound() {
		if(this.hasSound)
			missileSound.play();
	}
	public void stopSound() {
		if(this.hasSound)
			missileSound.stop();		
	}

	public boolean isFollowing() {
		return !this.s.equals(null);
	}
}
