import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
	private JLabel[] equipped = {new JLabel(), new JLabel(), new JLabel(), new JLabel()};
	private int skillWidth;
	private int equippedWidth;
	private int skillsPerRow = 2;
	private int selectedSlot = 0;
	private int selectedSkill = -1;
	private int skillLibraryWidth;
	private int skillLibraryHeight;
	private FontMetrics fm;
	
	public EquipPanel(Main m, int WIDTH, int HEIGHT) {
		this.m = m;
		this.setLayout(null);
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.setSize(WIDTH, HEIGHT);
		this.setVisible(true);
		this.setLayout(null);
		
		this.skillWidth = percentX(5);
		this.equippedWidth = (int) (2 * skillWidth);
		this.skillLibraryWidth = percentX(30);
		this.skillLibraryHeight = percentY(55);
		
		
		skills = new JLabel[Skill.amtOfSkills()];
		//Creation of equipped skills
		int count = 0;
		int xx = percentX(37.5);
		for(JLabel jl : equipped) {
			jl.setVisible(true);
			jl.addMouseListener(m.events.switchSkill);
			jl.setBounds(xx, percentY(40), equippedWidth, equippedWidth);
			jl.setText(String.valueOf(count));
			this.add(jl);
			count++;
			xx += percentX(15);
		}
		
		
		//Creation of skill library
		int x = percentX(5);
		int y = percentY(10);
		count = 0;
		int xCount = 0;
		
		for(Entry<String, Skill> s : Skill.getSkills().entrySet()) {
			if (xCount + 1 > skillsPerRow) {
				x = percentX(5);
				y += skillWidth + percentX(2.5);
				xCount = 0;
			}
			
			if(s.getValue().getImg() != null) {

				this.skills[count] = new JLabel(new ImageIcon(s.getValue().getImg(skillWidth, skillWidth)));
				this.skills[count].setVisible(true);
				this.skills[count].addMouseListener(m.events.skillSelect);
				this.skills[count].setBounds(x, y, skillWidth, skillWidth);
				this.skills[count].setText(s.getKey());
				this.add(this.skills[count]);
			}			

			x += skillWidth + percentX(2.5);

			xCount ++;
			count ++;

		}

		back.setBounds(percentX(60), percentY(80), percentX(10), percentY(7.5));
		this.add(back);
		back.setVisible(true);
		back.addActionListener(m.events.backtoMenu);
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
//		g.setColor(Color.YELLOW);
		g.setColor(Color.BLACK);
		for(int i = 0; i < this.getWidth(); i += this.getWidth() / 10) {
			g.drawLine(i, 0, i, this.getHeight());
		}
		for(int i = 0; i < this.getHeight(); i += this.getHeight() / 10) {
			g.drawLine(0, i, this.getWidth(), i);
		}
		
		
		//background for library
		g.setColor(new Color(100,100,100,200));
		g.fillRect(0, 0, skillLibraryWidth, skillLibraryHeight);
		
		g.setColor(new Color(200,200,200,200));
		g.fillRect(0, skillLibraryHeight, skillLibraryWidth, this.getHeight());
			
		
		g.setFont(new Font("Monospaced", Font.BOLD, percentY(5)));
		g.setColor(Color.WHITE);
		fm = g.getFontMetrics();
		
		g.drawString("SKILLS", (this.skillLibraryWidth - fm.stringWidth("SKILLS")) / 2, percentY(5));
		g.drawString("DESCRIPTION", (this.skillLibraryWidth - fm.stringWidth("DESCRIPTION")) / 2, this.skillLibraryHeight + percentY(5));
		
		
		for(int i = 0; i < this.skills.length; i++) {
			if(i == selectedSkill) {
				int selectedOffset = percentY(0.5);
				JLabel jl = skills[i];
				g.setColor(Color.RED);
				g.fillRect(jl.getX() - selectedOffset, jl.getY() - selectedOffset, jl.getWidth() + 2 * selectedOffset, jl.getHeight() + 2 * selectedOffset);
			}
		}
		
		int count = 0;
		for(JLabel jl : equipped) {
			jl.setIcon(new ImageIcon(m.ship.skills[count].getImg(equippedWidth, equippedWidth)));
		count ++;
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
	
	public String getSelectedSkillName() {
		return skills[selectedSkill].getText();
	}
	
	public void switchSkillSelection(String selectionName) {
		for(int i = 0; i < this.skills.length; i++) {
			if(this.skills[i].getText() == selectionName) {
				this.selectedSkill = i;
			}
		}
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
