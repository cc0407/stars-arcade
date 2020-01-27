import java.awt.Rectangle;

public class Missile {
	private Sound missileSound = new Sound("res\\pew2.wav");
	private final int MISSILE_SPEED;
	private final boolean hasSound;
	public Rectangle hitbox;
	
	//w=50, h =5
	public Missile(int x, int y, int width, int height, int speed, boolean hasSound) {
		hitbox = new Rectangle(x, y, width , height);
		MISSILE_SPEED = speed;
		this.hasSound = hasSound;
//		hitbox.x = x;
//		hitbox.y = y;
//		hitbox.x = x + 50;
//		hitbox.y = y + 30;
		if(hasSound)
			startSound();   
	}

	public void move() {
		hitbox.x += MISSILE_SPEED;
	}

	public int getX() {
		return hitbox.x;
	}

	public int getY() {
		return hitbox.y;
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

}
