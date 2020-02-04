import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

public class Events{
	private Main m;
	public final ActionListener play;
	public final ActionListener quit;
	public final ActionListener restart;
	public final ActionListener backtoMenu;
	public final ActionListener openEquip;
	public final MouseListener equipmentSelect;
	public Events(Main m) {
		this.m = m;
		
		play =new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	m.f.initGame();
		    }
		};
		
		quit = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	System.exit(0);
		    }
		};
		
		restart = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	m.f.initMenu();
		    }
		};
		
		backtoMenu = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	m.f.showMain();
		    }
		};
		
		openEquip = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	m.f.showEquip();
		    }
		};
		
		equipmentSelect = new MouseListener() {
		    @Override
		    public void mousePressed(MouseEvent e) {
		    	String text = ((JLabel) e.getComponent()).getText();
		    	m.ship.swapSkill(text, m.f.ep.getEquipSlot());
		    	m.f.repaint();
		    }
		    
			@Override
			public void mouseClicked(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		};
		
		
	}
}
