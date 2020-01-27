import java.awt.Rectangle;

public class Meteor {
	
	/** speed of meteor, in px per tick. Initialized through constructor. */
	private final int METEOR_SPEED;
	
	/** hitbox of meteor, this drives all of the movement and rendering.*/
	public Rectangle hitbox;
	
	/**
	 * Initializes a new meteor with set values.
	 * @param x the starting x component of the meteor, anchored to the upper left of the image
	 * @param y the starting y component of the meteor, anchored to the upper left of the image
	 * @param width the width of the meteor
	 * @param height the height of the meteor
	 * @param speed the speed at which it moves to the left of the screen, in px per tick
	 */
	public Meteor(int x, int y, int width, int height, int speed) {
		hitbox = new Rectangle(x, y, width, height);
		METEOR_SPEED = speed;
	}

	/**
	 * Moves the meteor towards the left of the screen. 
	 * @see METEOR_SPEED
	 */
	public void move() {
		hitbox.x -= METEOR_SPEED;
	}

	/** @return the X component of the meteor */
	public int getX() {
		return hitbox.x;
	}

	/** @return the Y component of the meteor */
	public int getY() {
		return hitbox.y;
	}
	
	/** @return the width component of the meteor */
	public int getWidth() {
		return hitbox.width;
	}
	
	/** @return the height component of the meteor */
	public int getHeight() {
		return hitbox.height;
	}
}
