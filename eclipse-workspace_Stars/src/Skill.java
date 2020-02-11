import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;

public class Skill {
	public static final LinkedHashMap<String, Skill> skills = new LinkedHashMap<String, Skill>();
	protected boolean isActive = false;
	protected final int duration;
	protected final int cooldown;
	protected int currentCooldown = 0;
	protected int startingCooldown;
	protected int ticksLeft = 0;
	protected String path;
	protected String description;
	protected Sound s;
	protected BufferedImage img;
	
	public Skill(int duration, int cooldown, int startingCooldown, String filename, String description) {
		this.duration = duration;
		this.ticksLeft = duration;
		this.cooldown = cooldown;
		this.startingCooldown = startingCooldown;
		this.currentCooldown = startingCooldown;
		this.path = filename;
		this.description = description;
		
		
		try {
			img = ImageIO.read(new File(filename + ".png"));
		}catch(IOException e) {
		}
	}	
	public Skill(int duration, int cooldown, String filename, String description) {
		this(duration, cooldown, 0, filename, description);
	}
	
	public Skill(Skill other) {
		this(other.getDuration(), other.getCooldown(), other.getStartingCooldown(), other.getPath(), other.getDescription());
	}
	
	public void play() {
		try{
		Sound.get(this.getName()).play();
		} catch(NullPointerException e) {}
	}
	public void pause() {
		try{
		Sound.get(this.getName()).pause();
		} catch(NullPointerException e) {}
	}
	public int getDuration() {
		return this.duration;
	}
	
	public int getDurationInSeconds(Main m) {
		return (int) (this.duration / m.amountOfTicks);
	}
	
	public int getCooldown() {
		return this.cooldown;
	}
	
	public int getCooldownInSeconds(Main m) {
		return (int) (this.cooldown / m.amountOfTicks);
	}
	
	public int getCurrentCooldown() {
		return this.currentCooldown;
	}
	
	public int getStartingCooldown() {
		return this.startingCooldown;
	}
	
	public int getStartingCooldownInSeconds(Main m) {
		return (int) (this.startingCooldown / m.amountOfTicks);
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getName() {
		String name = getPath().split("\\\\")[1];
		return name.toLowerCase() ;
	}
	
	public BufferedImage getImg() {
		return this.img;
	}
	
	public Image getImg(int width, int height) {
		Image dimg = this.getImg().getScaledInstance(width, height,
		        Image.SCALE_SMOOTH);
		return dimg;
	}
	
	public void editDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public boolean isOnCooldown() {
		if(this.currentCooldown > 0) {
			this.decreaseCooldown();
			return true;
		}
		else {
			return false;
		}
	}

	public boolean start() {
		if(currentCooldown <= 0 && !this.isActive) {
			this.isActive = true;
			this.play();
			return true;
		}
		return false;
	}

	public int decreaseTick() {
		if(!this.isActive) {
			return -1;
		}
		if(ticksLeft <= 0) {
			ticksLeft = duration;
			currentCooldown = cooldown;
			isActive = false;
			Sound.get(this.getName()).stop();
			return 0;
		}
		this.ticksLeft --;
		return ticksLeft;
	}

	public void decreaseCooldown() {
		if(this.currentCooldown > 0) {
			this.currentCooldown --;
		}
	}
	
	public double percentRemaining() {
		double result = (ticksLeft + 0.0) / (duration + 0.0);
		return result;
	}
	//returns cooldown as int between 0 and 360, for use by cooldown circles
	public int getArcCooldown() {
		int arcCD = (int) (360.0 * (this.currentCooldown + 0.0) / (this.cooldown + 0.0));
		return arcCD;
	}
	public void reset() {
		this.ticksLeft = duration;
		this.currentCooldown = startingCooldown;
		this.isActive = false;
	}
	
	public static void initSkills() {
		skills.put("SHIELD", new Skill(300, 600, "res\\Shield", "Forcefield that protects the ship from damage."));
		skills.put("MULTI", new Skill(600, 1200, "res\\Multi", "New guns open on each wing, allowing the ship to shoot three missiles instead of one."));
		skills.put("BOOST", new Skill(600, 1200, "res\\Boost", "Activates afterburners, increasing ships speed 1.5x."));
		skills.put("MEGA", new Skill(300, 3000, 1200, "res\\Mega", "Charges flux capacitors, allowing for huge release of energy in the form of a mega beam. Due to this, it takes time to charge up."));
		skills.put("MINE", new Skill(1, 1200, "res\\Mine", "Creates an energy mine that stays in place. Can be hit 3 times before breaking, part of the energy is used up in this process."));
		
	}
	public static HashMap<String, Skill> getSkills() {
		return new HashMap<String, Skill>(skills);
	}
	
	public static Skill get(String key) {
		return skills.get(key);
	}
	
	public static int amtOfSkills() {
		return skills.size();
	}
}
