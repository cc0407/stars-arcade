import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {
	
	
	//TODO destruction particles and animation
	//TODO animations for power-ups to show that they're active
	//TODO meteor textures
	//TODO powerup textures
	//TODO 6 Boss battles
	//TODO add sounds
	//TODO implement more powerups, refactor the 4 in renderer to pull from 4 "active skills" inside ship class
	

	public Frame f;
	public World world;
	public Ship ship;
	public Tick t;
	public boolean run;

	public static boolean running = true;

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f = new Frame(this, screenSize);
		f.init();
		this.ship = new Ship(this);
		t = new Tick(this);
		run();
	}

	public void run() {
		run = true;
		f.start();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60D;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		while (running) {

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			if (delta >= 1) {
				delta--;
				t.update();

			}
		}
	}

}
