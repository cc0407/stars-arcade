
import java.awt.Rectangle;
import java.util.ArrayList;

public class Missile {
	private Sound missileSound = new Sound("res\\pew2.wav");
	private final int MISSILE_SPEED = 15;

	public Rectangle hitbox;
	
	//w=50, h =5
	public Missile(int x, int y, int width, int height) {
		hitbox = new Rectangle(width , height);
		hitbox.x = x + 50;
		hitbox.y = y + 30;
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
		missileSound.pause();
	}

	public void startSound() {
		missileSound.play();
	}
	public void stopSound() {
		missileSound.stop();
	}

}
