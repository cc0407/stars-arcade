import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

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
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		g.setColor(Color.yellow);
		g.fillRect(0, 0, m.f.WIDTH, m.f.HEIGHT);
	}
}
