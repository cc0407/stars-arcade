import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//TODO shield sound does not play
public class Sound {

	private Clip clip;
	private long clipTime = 0;
	private static AudioInputStream ais;
	
	public Sound(String fileName) {
		try {
			File url = new File(fileName);
	    	ais = AudioSystem.getAudioInputStream( url );
			clip = AudioSystem.getClip();	
			clip.open(ais);
		} catch (LineUnavailableException | UnsupportedAudioFileException| IOException e1) {
			e1.printStackTrace();
		} 
	}
	
	public void play(){
			clip.setMicrosecondPosition(clipTime);
			clip.start();
	}
	
	public void pause() {
			clipTime = clip.getMicrosecondPosition();
			clip.stop();
	}

}
