package projektv4;

import javax.swing.SwingUtilities;

public class Play implements Runnable{
	public void run() {
	       SwingUtilities.invokeLater(new MainMenu());
	}
	    
	public static void main(String[] args) {
	       SwingUtilities.invokeLater(new Play());
	}
}
