import java.awt.Rectangle;
import java.util.ArrayList;

public class Meteor {
	private final int METEOR_SPEED = 2;
	
	public Rectangle hitbox;
	
	public Meteor(int x, int y) {
		hitbox = new Rectangle(64 , 64);
		hitbox.x = x;
		hitbox.y = y;
	}

	public void move() {
		hitbox.x -= METEOR_SPEED;
	}

	public int getX() {
		return hitbox.x;
	}

	public int getY() {
		return hitbox.y;
	}

}
