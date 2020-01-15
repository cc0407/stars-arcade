import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sounds {

	private Clip clip, clip2, clip3, clip4;
	private long clip2Time, clip3Time, clip4Time;
	
	public void playSound(String fileName){
		// throws MalformedURLException, LineUnavailableException, UnsupportedAudioFileException, IOException
		
		try {
			File url = new File(fileName);
			
	    	AudioInputStream ais = AudioSystem.
	   		getAudioInputStream( url );
	    	
	    	if(fileName.equals("res\\pew2.wav"))
	    	{
				clip = AudioSystem.getClip();
		    	clip.open(ais);
		    	clip.start();
	    	}
	    	else if(fileName.equals("res\\Shield.wav"))
	    	{
			clip2 = AudioSystem.getClip();
	    	clip2.open(ais);
	    	clip2.start();
	    	}
	    	/*
	    	else if(fileName.equals("res\\.wav"))
	    	{
			clip3 = AudioSystem.getClip();
	    	clip3.open(ais);
	    	clip3.start();
	    	}
	    	else if(fileName.equals("res\\.wav"))
	    	{
			clip4 = AudioSystem.getClip();
	    	clip4.open(ais);
	    	clip4.start();
	    	}
*/

	    	
	    	

	    		
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
	public void pauseSound() {
		//clip3Time = clip3.getMicrosecondPosition();
		//clip4Time = clip4.getMicrosecondPosition();
		

		//clip3.stop();
		//clip4.stop();
		
		if(Ship.shield)
		{
			clip2Time = clip2.getMicrosecondPosition();
			clip2.stop();
		}
	}
	
	public void resumeSound() {

		//clip3.setMicrosecondPosition(clip3Time);
		//clip4.setMicrosecondPosition(clip4Time);
		

		//clip3.start();
		//clip4.start();
		
		if(Ship.shield)
		{
			clip2.setMicrosecondPosition(clip2Time);
			clip2.start();
		}
		
	}
}
