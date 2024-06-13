package projektv4;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundHandler {

	static Clip clip;
	
	public static void playMusic(String path) {
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void stopMusic(){
		clip.close();
	}
	
	public static void pauseMusic() {
		clip.stop();
	}
	
	public static void resumeMusic() {
		clip.start();
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public static void changeVolume(int n) {
		FloatControl gainControl = 
			    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(n);
	}
}
