import java.awt.Rectangle;
import java.util.ArrayList;

public class Meteor {
	private final int METEOR_SPEED;
	
	public Rectangle hitbox;
	
	public Meteor(int x, int y, int width, int height, int speed) {
		hitbox = new Rectangle(x, y, width, height);
		METEOR_SPEED = speed;
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
