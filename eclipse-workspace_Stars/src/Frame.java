
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class Frame extends JFrame{
	public int HEIGHT;
	public int WIDTH;
	public Renderer jp;
	public MenuPanel mp;
	public EquipPanel ep;
	public Main m;
	public boolean paused = false;
	
	public Frame(Main m) {
		this(m, new Dimension(1920,1080));
	}
	
	public Frame(Main m, Dimension screen) {
		this.setTitle("Space");
		WIDTH = (int) screen.getWidth();
		HEIGHT = (int) screen.getHeight();
		this.m = m;
		
		this.setSize(WIDTH, HEIGHT);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setAlwaysOnTop(false);
		this.getContentPane().setLayout(null);
		
		jp = new Renderer(m, WIDTH, HEIGHT);     
		mp = new MenuPanel(m, WIDTH, HEIGHT);
		ep = new EquipPanel(m, WIDTH, HEIGHT);
		this.add(jp);
		this.add(mp);
		this.add(ep);
		


	}
	
	public void initMenu() {
		m.ship = new Ship(m);
		m.ship.alive = false;
		m.world = new World(m);
		m.t = new Tick(m);

		showMain();
		this.requestFocus();
		this.setVisible(true);
		this.repaint();

		if(!m.run)
			m.run();


	}
	public void initGame() {
		jp.setVisible(true);
		m.ship.resurrect();
		mp.setVisible(false);
//		this.remove(mp);
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
	
	public void pause() {
		if(!this.paused) {
			togglePause();
		}
	}
	
	public void unpause() {
		if(this.paused) {
			togglePause();
		}
	}
	
	public void showMain() {
		pause();
		jp.setVisible(false);
		mp.setVisible(true);
		ep.setVisible(false);
	}
	
	public void showEquip() {
		m.f.ep.setVisible(true);
    	m.f.mp.setVisible(false);
    	m.f.jp.setVisible(false);
	}


}
