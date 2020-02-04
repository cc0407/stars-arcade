
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class Frame {
	public int HEIGHT;
	public int WIDTH;
	public Renderer jp;
	public MenuPanel mp;
	public JFrame jf = new JFrame("Space");
	public Main m;
	public boolean paused = false;
	
	public Frame(Main m) {
		this(m, new Dimension(1920,1080));
	}
	
	public Frame(Main m, Dimension screen) {
		WIDTH = (int) screen.getWidth();
		HEIGHT = (int) screen.getHeight();
		this.m = m;

		
		jf.setSize(WIDTH, HEIGHT);
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		jf.setUndecorated(true);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setAlwaysOnTop(false);
		jf.getContentPane().setLayout(null);
		
		jp = new Renderer(m, WIDTH, HEIGHT);     
		mp = new MenuPanel(m, WIDTH, HEIGHT);

	}
	
	public void initMenu() {
		m.ship = new Ship(m);
		m.ship.alive = false;
		m.world = new World(m);
		m.t = new Tick(m);
		jf.add(jp);
		jp.setVisible(false);
		
		jf.add(mp);
		jf.requestFocus();
		jf.setVisible(true);
		jf.repaint();
		//pauses actual game while menu is above it
		togglePause();
		m.run();


	}
	public void initGame() {
		jp.setVisible(true);
		m.ship.resurrect();
		jf.remove(mp);
		togglePause();
	}
	
	
	public boolean togglePause() {
		jp.keybinds.freeze = !jp.keybinds.freeze;
		this.paused = !this.paused;
		jp.repaint();
		if(paused) {
			Sound.pauseAll();
			return true;
		}
		else {
			Sound.resumeAll();
			return false;
		}
	}


}
