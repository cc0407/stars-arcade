
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class Frame {
	public int HEIGHT;
	public int WIDTH;
	public Renderer jp;
	public MenuPanel mp;
	public JFrame jf = new JFrame("Space");
	public Main m;
	public boolean paused = true;
	
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
		jf.setAlwaysOnTop(true);
		jf.getContentPane().setLayout(null);
		
		jp = new Renderer(m, WIDTH, HEIGHT);     
		jf.add(jp);
		
		mp = new MenuPanel(new GridBagLayout(), m);
		mp.setFocusable(true);
		mp.setSize(WIDTH, HEIGHT);
//		mp.setVisible(true);
//		jf.add(mp);
		
		
		jf.requestFocus();
	}
	
	public void initGame() {
		
		m.world = new World(m);
		m.t = new Tick(m);
		m.ship = new Ship(m);
		m.run();
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
