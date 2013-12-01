/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testbench.datext.testapps.rpgcharacter;
import datext.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	
	private static RPGCharacter instance = null;
	
	public static RPGCharacter getInstance(){
		if(instance == null){
			instance = new RPGCharacter();
		}
		return instance;
	}
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

	private DaTextObject config = null;
	private Path configFile = null;
	
	private void init() throws IOException{
		if(instance == null){
			instance = this;
		}
		configFile = Paths.get(dataDir.toString(), DATA_FILENAME);
		if(Files.notExists(configFile)){
			// unpack default file
			InputStream in = RPGCharacter.class.getResourceAsStream("/testbench/datext/testapps/rpgcharacter/config.dtx");
			Files.copy(in, configFile);
		}
		config = DaText.createObjectFromFile(configFile);
	}
	
	public DaTextObject getConfiguration(){
		return config;
	}

	private void createAndShowGUI() {
		DaTextList charList = config.getOrSet("characters", new java.util.ArrayList<DaTextVariable>(), "saved characters");
		MainFrame mf = new MainFrame(charList);
		mf.pack();
		mf.setLocationRelativeTo(null);
		mf.setVisible(true);
	}
	
	public void exitProgram(){
		try{
			DaText.writeObjectToFile(this.getConfiguration(),configFile);
		} catch(IOException ex){
			Logger.getLogger(this.getClass().getCanonicalName()).log(Level.SEVERE, "Error while writing data", ex);
		}
		System.exit(0);
	}
	
	
}
