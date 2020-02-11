import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	private String path;
	private Clip clip;
//	public static Sound missile = new Sound("res\\pew2.wav");
	private long clipTime = 0;
	private static AudioInputStream ais;
//	public final static ArrayList<Sound> sounds = new ArrayList<>();
	public static final LinkedHashMap<String, Sound> sounds = new LinkedHashMap<String, Sound>();
	// 1 = playing, 0 = stopped, -1 = paused
	public int playing = 0;
	
	public Sound(String fileName) {
		try {
			path = fileName;
			File url = new File(path);
	    	ais = AudioSystem.getAudioInputStream( url );
			clip = AudioSystem.getClip();	
			clip.open(ais);
		} catch (LineUnavailableException | UnsupportedAudioFileException| IOException e1) {
			e1.printStackTrace();
		} 
	}
	
	public void addToLoop() {
		if(this.playing != 1) {
			this.play();
			return;
		}
		clip.loop(1);
	}
	
	public void removeAllLoops() {
		clip.loop(0);
	}
	public void play(){
		try {
			if(this.playing != 1) {
				clip.setMicrosecondPosition(clipTime);
				clip.start();
				this.playing = 1;
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	public void pause() {
		try {
			if(this.playing != -1) {
				clipTime = clip.getMicrosecondPosition();
				clip.stop();
				this.playing = -1;
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
		
	}
	
	public void stop() {
		try {
			if(this.playing != 0) {
				
				clipTime = 0;
				clip.stop();
//				sounds.remove(this);
				
				//for garbage collection, clip.close() caused random freezing
//				clip = null;
//				clip.close();
				
				this.playing = 0;

			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
}
	public static void pauseAll() {
		for(Entry<String, Sound> e : sounds.entrySet()) {
			Sound s = e.getValue();
			if(s.playing == 1) {
				s.pause();
			}
		}
	}
	
	public static void resumeAll() {
		for(Entry<String, Sound> e : sounds.entrySet()) {
			Sound s = e.getValue();
			if(s.playing == -1) {
				s.play();
			}
		}
	}
	
	public static void stopAll() {
		for(Entry<String, Sound> e : sounds.entrySet()) {
			Sound s = e.getValue();
			if(s.playing == -1) {
				s.stop();
			}
		}
	}
	
	public static void initSounds() {
		sounds.put("missile", new Sound("res\\Pew2.wav"));
		sounds.put("shield", new Sound("res\\Shield.wav"));
		sounds.put("boost", new Sound("res\\Boost.wav"));
		sounds.put("multi", new Sound("res\\Multi.wav"));
		sounds.put("mega", new Sound("res\\Mega.wav"));
		sounds.put("mine", new Sound("res\\Mine.wav"));
	}

	public static Sound get(String sound) {
		return sounds.get(sound.toLowerCase());
	}
}
