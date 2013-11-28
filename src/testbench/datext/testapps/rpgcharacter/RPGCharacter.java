/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbench.datext.testapps.rpgcharacter;
import java.io.IOException;
import java.nio.file.*;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author cybergnome
 */
public class RPGCharacter {

	static final String DATA_DIRNAME = ".RPGCharacter";
	static final String DATA_FILENAME = "data.dtx";
	public static Path dataDir = Paths.get(DATA_DIRNAME); // working dir
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			
			if(System.getProperty("os.name").toLowerCase(Locale.US).contains("windows")){
				// is windows
				dataDir = Paths.get(System.getProperty("user.home"),"AppData","Roaming",DATA_DIRNAME);
			} else {
				// is not windows
				dataDir = Paths.get(System.getProperty("user.home"),DATA_DIRNAME);
			}
			System.out.println("Initializing data directory:\n\t"+dataDir.toAbsolutePath().toString());
			Files.createDirectories(dataDir);
			
			
			// TODO code application logic here
			final RPGCharacter program = new RPGCharacter();
			program.init();
			javax.swing.SwingUtilities.invokeLater(new Runnable(){public void run(){program.createAndShowGUI();}});
		} catch (Exception ex) {
			Logger.getLogger(RPGCharacter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private RPGCharacter() {
		// do Nothing
	}

	private void init() {
		// 
	}

	private void createAndShowGUI() {
		// 
	}
	
	
}
