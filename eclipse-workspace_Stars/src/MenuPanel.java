import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2050943726124305477L;
	private Main m;
	public MenuPanel(Main m) {
		this(null, m);
	}

	public MenuPanel(LayoutManager layout, Main m) {
		super(layout);
		this.m = m;
		this.setBackground(Color.BLACK);
		JButton test1 = new JButton("Test");		
		JButton test2 = new JButton("Test");
		
		this.add(Box.createVerticalGlue());
		this.add(test1);
		this.add(Box.createVerticalGlue());
		this.add(test2);
		this.add(Box.createVerticalGlue());
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, m.f.WIDTH, m.f.HEIGHT);
		
		g.drawImage(m.ship.getImage(), 50, 50, this);
	}
}
