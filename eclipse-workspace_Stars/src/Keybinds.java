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
		int key = e.getKeyCode();
		if(!freeze) 
		{
	
			if (key == KeyEvent.VK_A) {
				m.ship.setDirection(Direction.LEFT);
			}
	
			if (key == KeyEvent.VK_D) {
				m.ship.setDirection(Direction.RIGHT);
			}
	
			if (key == KeyEvent.VK_W) {
				m.ship.setDirection(Direction.UP);
			}
	
			if (key == KeyEvent.VK_S) {
				m.ship.setDirection(Direction.DOWN);
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
				m.ship.startSkill(0);
			}
			
			if (key == KeyEvent.VK_2) {
				m.ship.startSkill(1);
			}
			
			if (key == KeyEvent.VK_3) {
				m.ship.startSkill(2);
			}
			
			if (key == KeyEvent.VK_4) {
				m.ship.startSkill(3);
			}
		}
			if (key == KeyEvent.VK_ESCAPE) {

					if(!m.f.togglePause())
					m.f.jf.repaint();
				
			}
			
		
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(!freeze) 
		{
			if (key == KeyEvent.VK_A) {
				m.ship.stopDirection(Direction.LEFT);
			}
	
			if (key == KeyEvent.VK_D) {
				m.ship.stopDirection(Direction.RIGHT);
			}
	
			if (key == KeyEvent.VK_W) {
				m.ship.stopDirection(Direction.UP);
			}
	
			if (key == KeyEvent.VK_S) {
				m.ship.stopDirection(Direction.DOWN);
			}
			if (key == KeyEvent.VK_SPACE) {
				m.ship.isFiring = false;
			}
		}
		if (key == KeyEvent.VK_ESCAPE) {
			m.ship.stopPressingKeys();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
