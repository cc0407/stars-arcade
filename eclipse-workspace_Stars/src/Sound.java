import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//TODO shield sound does not play
public class Sound {

	private String path;
	private Clip clip;
	private long clipTime = 0;
	private static AudioInputStream ais;
	private final static ArrayList<Sound> sounds = new ArrayList<>();
	// 1 = playing, 0 = stopped, -1 = paused
	public int playing = 0;
	
	public Sound(String fileName) {
		try {
			path = fileName;
			File url = new File(path);
	    	ais = AudioSystem.getAudioInputStream( url );
			clip = AudioSystem.getClip();	
			clip.open(ais);
			sounds.add(this);
		} catch (LineUnavailableException | UnsupportedAudioFileException| IOException e1) {
			e1.printStackTrace();
		} 
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
				this.playing = 0;
				sounds.remove(this);
			}
		}
		catch(NullPointerException e) {
			e.printStackTrace();
		}
}
	public static void pauseAll() {
		for(Sound s : sounds) {
			if(s.playing == 1) {
				s.pause();
			}
		}
	}
	
	public static void resumeAll() {
		for(Sound s : sounds) {
			if(s.playing == -1) {
				s.play();
			}
		}
	}
	
	public static void clearAll() {
		sounds.clear();
	}

}
