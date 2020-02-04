import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EquipPanel extends JPanel {

	Main m;

	private JButton back = new JButton("back");
	private JLabel[] skills;
	private int equipWidth;
	private int skillsPerRow = 3;
	private int selectedSlot = 0;
	public EquipPanel(Main m, int WIDTH, int HEIGHT) {
		this.m = m;
		this.setLayout(null);
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setLayout(null);
		
		equipWidth = percentY(10);
		skills = new JLabel[Skill.amtOfSkills()];
		back.setBounds(percentX(90) / 2, percentY(70), percentX(10), percentY(5));
		this.add(back);
		back.setVisible(true);
		back.addActionListener(m.events.backtoMenu);
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		
		
		
		
		//background for library
		g.setColor(new Color(100,100,100,200));
		g.fillRect(0, 0, percentY(50), percentY(50));
		
		//Drawing of equipment library
		int x = percentY(10);
		int y = percentY(10);
		int count = 0;
		for(Entry<String, Skill> s : Skill.getSkills().entrySet()) {
			if(s.getValue().getImg() != null) {

				this.skills[count] = new JLabel(new ImageIcon(s.getValue().getImg(equipWidth, equipWidth)));
				this.skills[count].setVisible(true);
				this.skills[count].addMouseListener(m.events.equipmentSelect);
				this.skills[count].setBounds(x, y, equipWidth, equipWidth);
				this.skills[count].setText(s.getKey());
				this.add(this.skills[count]);
			}
			
			
			x+= equipWidth;
			if (x >= skillsPerRow * equipWidth) {
				x = percentY(10);
				y += equipWidth;
			}
			
		}
		
		
		//drawing of equiped skills
		int xx = 200;
		for(Skill s : m.ship.skills) {
			g.drawImage(s.getImg(), xx, 400, 50, 50,this);
			xx += 50;
		}
	}

	public int nextEquip() {
		int oldSlot = this.selectedSlot;
		this.selectedSlot++;
		if(this.selectedSlot >= 4) {
			this.selectedSlot = 0;
		}
		return oldSlot;
	}
	
	public int getEquipSlot() {
		return this.selectedSlot;
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
