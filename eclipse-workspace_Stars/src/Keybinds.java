import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;


public class Keybinds extends JComponent implements KeyListener {

	/**
		  
		 */
	private static final long serialVersionUID = 1L;
	public boolean freeze = false;
		
	private Main m;
	public Keybinds(Main m) {
		this.m = m;
	}
	
	public void keyPressed(KeyEvent e) {

		
		if(!freeze) 
		{
			int key = e.getKeyCode();
	
			if (key == KeyEvent.VK_A) {
				m.ship.dx = -m.ship.speed;
			}
	
			if (key == KeyEvent.VK_D) {
				m.ship.dx = m.ship.speed;
			}
	
			if (key == KeyEvent.VK_W) {
				m.ship.dy = -m.ship.speed;
			}
	
			if (key == KeyEvent.VK_S) {
				m.ship.dy = m.ship.speed;
			}
			if (key == KeyEvent.VK_SPACE) {
				if(m.ship.alive)
					m.ship.isFiring = true;
				else
				{
					m.ship.resurrect();
					m.t.count =0;
				}
			}
			
			
			if (key == KeyEvent.VK_1) {
				m.ship.shield.start();
			}
			
			if (key == KeyEvent.VK_2) {
				m.ship.multi.start();
			}
			
			if (key == KeyEvent.VK_3) {
				if(m.ship.boost.start()){
					m.ship.speed = 15;
				}
			}
			
			if (key == KeyEvent.VK_4) {
				m.ship.mega.start();
			}
			
			if (key == KeyEvent.VK_ESCAPE) {

//					TODO fix sounds to mute all on pause
//					Sounds.resumeSound();
//				Sounds.pauseSound();
					if(!m.f.togglePause())
					m.f.jf.repaint();
				
			}
			
		}
	}

	public void keyReleased(KeyEvent e) {
		
		if(!freeze) 
		{
			int key = e.getKeyCode();
	
			if (key == KeyEvent.VK_A) {
				m.ship.dx = 0;
			}
	
			if (key == KeyEvent.VK_D) {
				m.ship.dx = 0;
			}
	
			if (key == KeyEvent.VK_W) {
				m.ship.dy = 0;
			}
	
			if (key == KeyEvent.VK_S) {
				m.ship.dy = 0;
			}
			if (key == KeyEvent.VK_SPACE) {
				m.ship.isFiring = false;
			}
			
			if (key == KeyEvent.VK_1) {

			}
			
			if (key == KeyEvent.VK_2) {

			}
			
			if (key == KeyEvent.VK_3) {
			}
			
			if (key == KeyEvent.VK_ESCAPE) {
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	
	}
}
