import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2050943726124305477L;
	private Main m;
	private JButton play = new JButton("play");
	private JButton equipment = new JButton("equipment");
	private JButton quit = new JButton("quit");
	private int currentSelectionY = -100;
	private Rectangle ButtonSize;
	FontMetrics fm;
	public MenuPanel(Main m, int WIDTH, int HEIGHT) {
//		this(null, m);
		this.m = m;
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setLayout(null);
		ButtonSize = new Rectangle(percentX(15), percentY(8));
		
		play.setBounds(percentX(85) / 2, percentY(30), ButtonSize.width, ButtonSize.height);
		this.add(play);
		play.addMouseListener(new MouseEntered());
		play.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
//		    	play.setText("test");
		    	m.f.initGame();
		    }
		});
		
		
		equipment.setBounds(percentX(85) / 2, percentY(50), ButtonSize.width, ButtonSize.height);
		this.add(equipment);
		equipment.addMouseListener(new MouseEntered());
		
		
		quit.setBounds(percentX(85) / 2, percentY(70), ButtonSize.width, ButtonSize.height);
		this.add(quit);
		quit.addMouseListener(new MouseEntered());
		quit.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	System.exit(0);
		    }
		});
		this.repaint();
	}
	

//	public MenuPanel(LayoutManager layout, Main m) {
//		super(layout);
//		
//	}

	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, m.f.WIDTH, m.f.HEIGHT);
		
		g.setFont(new Font("Monospaced", Font.PLAIN, percentY(8)));
		fm = g.getFontMetrics();
		g.setColor(Color.WHITE);
		g.drawString("STARS ARCADE", (this.getWidth() - fm.stringWidth("STARS ARCADE"))/2, (percentY(40) - fm.getHeight())/2);
		
		
		g.drawImage(m.ship.getImage(), percentX(80) / 2 - percentX(4.6), currentSelectionY, ButtonSize.height, ButtonSize.height,this);
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
	
	
	
	class MouseEntered extends MouseAdapter{
	    public void mouseEntered(MouseEvent evt) {
	    	currentSelectionY = evt.getComponent().getY();
	    }

	    public void mouseExited(MouseEvent evt) {
//	    	currentSelectionY = -m.ship.getHeight();
//	    	repaint();
	    }
	}
}


