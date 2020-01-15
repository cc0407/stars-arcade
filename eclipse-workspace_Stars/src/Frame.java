
import java.awt.Color;
import java.awt.Dimension;

import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame {
	public int HEIGHT;
	public int WIDTH;
	public Renderer jp;
	public Clip pew;
	public JFrame jf = new JFrame("Space");
	public Main m;
	public Keybinds keybinds;
	public boolean paused = true;
	
	public Frame(Main m) {
		this(m, new Dimension(1920,1080));
	}
	
	public Frame(Main m, Dimension screen) {
		WIDTH = (int) screen.getWidth();
		HEIGHT = (int) screen.getHeight();
		this.m = m;
		this.keybinds = new Keybinds(m);
		jp = new Renderer(m);

		
		jf.setSize(WIDTH, HEIGHT);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setAlwaysOnTop(true);
		jf.getContentPane().setLayout(null);

		jp.addKeyListener(keybinds);
		jp.setFocusable(true);
		jp.setBackground(Color.BLACK);
		jp.setSize(WIDTH, HEIGHT);
		jp.setVisible(true);

     
		jf.add(jp);
	}
	
	public void init() {
		m.world = new World(m);
	}
	
	public void start() {
		jf.setVisible(true);
		paused = false;
	}
	//TODO UNPAUSING DOESNT WORK
	public boolean togglePause() {
		this.keybinds.freeze = !this.keybinds.freeze;
		paused = !paused;
		if(paused)
			return true;
		else
			return false;
	}
	public void pause() {
		this.keybinds.freeze = true;
		paused = true;
	}
	public void unpause() {
		this.keybinds.freeze = false;
		paused = false;
	}

}
