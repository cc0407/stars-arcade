import java.awt.Dimension;
import java.awt.Toolkit;

public class Main {
	
	
	//TODO destruction particles and animation
	//TODO animations for power-ups to show that they're active
	//TODO meteor textures
	//TODO powerup textures
	//TODO 6 Boss battles
	//TODO add sounds
	//TODO implement more powerups
	//TODO refactor background particles so it doesn't use a 2D array
	//TODO locate and fix privacy leaks
	//TODO start menu
	//TODO particles dont scale
	
	//TODO color is taking a lot of memory for some reason
	//TODO Sound clips are not closing
	public Frame f;
	public World world;
	public Ship ship;
	public Tick t;
	public Events events;
	public boolean run;
	public boolean running = true;

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		events = new Events(this);
		Skill.initSkills();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f = new Frame(this, screenSize);
		
//		f.initGame();
//		Commented out while developing, will swap to it once finished
		f.initMenu();
	}

	public void run() {
		run = true;
//		f.jf.setVisible(true);
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
