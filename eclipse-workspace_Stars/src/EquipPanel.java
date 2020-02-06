import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class EquipPanel extends JPanel {

	Main m;

	private JButton back = new JButton("back");
	private JLabel[] skills;
	private JLabel[] equipped = {new JLabel(), new JLabel(), new JLabel(), new JLabel()};
	private JTextArea descArea = new JTextArea();
	private JTextArea skillDetailArea = new JTextArea();
	private int skillWidth;
	private int equippedWidth;
	private int skillsPerRow = 3;
	private int selectedSlot = 0;
	private int selectedSkill = -1;
	private int skillLibraryWidth;
	private int skillLibraryHeight;
	private String currentDescription = "";
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
			jl.setName(String.valueOf(count));
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
				this.skills[count].setName(s.getKey());
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
		
		descArea.setBounds(0, this.skillLibraryHeight + percentY(7.5), this.skillLibraryWidth, this.getHeight() - this.skillLibraryHeight - percentY(27.5));
		this.add(descArea);
		descArea.setEditable(false);
		descArea.setBackground(new Color(0,0,0,0));
		descArea.setLineWrap(true);
		descArea.setWrapStyleWord(true);
		descArea.setFont(new Font("Monospaced", Font.BOLD, percentY(2)));
		descArea.setForeground(Color.WHITE);
		
		skillDetailArea.setBounds(0, percentY(80), this.skillLibraryWidth, percentY(20));
		this.add(skillDetailArea);
		skillDetailArea.setEditable(false);
		skillDetailArea.setBackground(new Color(0,0,0,0));
		skillDetailArea.setLineWrap(true);
		skillDetailArea.setWrapStyleWord(true);
		skillDetailArea.setFont(new Font("Monospaced", Font.BOLD, percentY(2)));
		skillDetailArea.setForeground(Color.WHITE);
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
		
		//background for description
		g.setColor(new Color(200,200,200,200));
		g.fillRect(0, skillLibraryHeight, skillLibraryWidth, this.getHeight());
			
		
		//draws titles
		g.setFont(new Font("Monospaced", Font.BOLD, percentY(5)));
		g.setColor(Color.WHITE);
		fm = g.getFontMetrics();
		
		g.drawString("SKILLS", (this.skillLibraryWidth - fm.stringWidth("SKILLS")) / 2, percentY(5));
		
		if(!this.hasASelection())
			g.drawString("DESCRIPTION", (this.skillLibraryWidth - fm.stringWidth("DESCRIPTION")) / 2, this.skillLibraryHeight + percentY(5));
		else
			g.drawString(getSelectedSkillName(), (this.skillLibraryWidth - fm.stringWidth(getSelectedSkillName())) / 2, this.skillLibraryHeight + percentY(5));
		
		//Draws red selection rectangle on skill library
		for(int i = 0; i < this.skills.length; i++) {
			if(i == selectedSkill) {
				int selectedOffset = percentX(0.25);
				JLabel jl = skills[i];
				g.setColor(Color.RED);
				g.fillRect(jl.getX() - selectedOffset, jl.getY() - selectedOffset, jl.getWidth() + 2 * selectedOffset, jl.getHeight() + 2 * selectedOffset);
			}
		}
		
		//sets new icon for equipped skills
		int count = 0;
		for(JLabel jl : equipped) {
			jl.setIcon(new ImageIcon(m.ship.skills[count].getImg(equippedWidth, equippedWidth)));
		count ++;
		}
		

	}
	
	
	public String getSelectedSkillName() {
			return skills[selectedSkill].getName();
	}
	
	public void setSelectedSkillDescription(String name) {
		Skill s = Skill.skills.get(name);
		this.currentDescription = s.getDescription();
		this.descArea.setText(this.currentDescription);
		this.skillDetailArea.setText("Duration: " + s.getDurationInSeconds(m) + " sec\n" + "Cooldown: " + s.getCooldownInSeconds(m) + " sec");
		if(s.getStartingCooldown() != 0) {
			this.skillDetailArea.append("\nCharge-Up: " + + s.getStartingCooldownInSeconds(m) + " sec");
		}
	}
	
	public boolean hasASelection() {
		boolean hasSelection = true;
		if(this.selectedSkill == -1) {
			hasSelection = false;
		}
		return hasSelection;
	}
	
	public void switchSkillSelection(String selectionName) {
		for(int i = 0; i < this.skills.length; i++) {
			if(this.skills[i].getName() == selectionName) {
				this.selectedSkill = i;
			}
		}
		setSelectedSkillDescription(selectionName);
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
