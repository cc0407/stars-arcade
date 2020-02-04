import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EquipPanel extends JPanel {

	Main m;
	private JButton back = new JButton("back");
	public EquipPanel(Main m, int WIDTH, int HEIGHT) {
		this.m = m;
		this.setLayout(null);
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setLayout(null);
		
		back.setBounds(percentX(90) / 2, percentY(70), percentX(10), percentY(5));
		this.add(back);
		back.setVisible(true);
		back.addActionListener(m.events.backtoMenu);
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
	}

	public int percentX(int percent) {
		return this.getWidth() * percent / 100;
	}
	
	public int percentY(int percent) {
		return this.getHeight() * percent / 100;
	}
	
	public int percentX(double percent) {
		double result = this.getWidth() * percent / 100.0;
		return (int) result;
	}
	
	public int percentY(double percent) {
		double result = this.getHeight() * percent / 100.0;
		return (int) result;
	}
}
