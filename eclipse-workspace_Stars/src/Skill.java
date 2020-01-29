import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Skill {
	public static final Skill SHIELD = new Skill(300, 600, "res\\Shield");
	public static final Skill MULTI = new Skill(600, 1200, "res\\Multi");
	public static final Skill BOOST = new Skill(600, 1200, "res\\Boost");
	public static final Skill MEGA = new Skill(300, 3200, "res\\Mega");
	protected boolean isActive = false;
	protected final int duration;
	protected final int cooldown;
	protected int currentCooldown = 0;
	protected int startingCooldown;
	protected int ticksLeft = 0;
	protected String path;
	protected Sound s;
	protected BufferedImage img;
	
	public Skill(int duration, int cooldown, int startingCooldown, String filename) {
		this.duration = duration;
		this.ticksLeft = duration;
		this.cooldown = cooldown;
		this.startingCooldown = startingCooldown;
		this.currentCooldown = startingCooldown;
		this.path = filename;
		
		s = new Sound(filename + ".wav");
		
		
		try {
			img = ImageIO.read(new File(filename + ".png"));
		}catch(IOException e) {
		}
	}
	
	public Skill(int duration, int cooldown, String filename) {
		this(duration, cooldown, 0, filename);
	}
	
	public Skill(Skill other) {
		this(other.getDuration(), other.getCooldown(), other.getStartingCooldown(), other.getPath());
	}
	
	public void play() {
		this.s.play();
	}
	public void pause() {
		this.s.pause();
	}
	public int getDuration() {
		return this.duration;
	}
	public int getCooldown() {
		return this.cooldown;
	}
	
	public int getCurrentCooldown() {
		return this.currentCooldown;
	}
	
	public int getStartingCooldown() {
		return this.startingCooldown;
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
			this.s.stop();
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
	
}
