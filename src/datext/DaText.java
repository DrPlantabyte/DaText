/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicBoolean;

// TODO: update the documentation, 
// TODO: add high-performance alternative implementations, 
// TODO: add methods to the DaText helper to create useful instances of DaText objects

/**
 * This utility class provides static methods for reading, writing, and 
 * creating DaText data.
 * @author Christopher Collin Hall
 */
public abstract class DaText {
	// TODO documentation
	public final static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	/** If set to <code>true</code>, then numbers will be formatted with a 
	 * comma as the decimal mark (European style).
	 */
	public final static boolean useCommaAsDecimalMark = false;
	
	public static DaTextObject createObjectFromFile(Path file) throws IOException, IllegalArgumentException{
		if(!Files.exists(file)){
			// file does not exist, make a new datext object
			return new DefaultObject();
		}
		DefaultDaTextParser p = new DefaultDaTextParser();
		DaTextObject obj;
		try (Reader reader = Files.newBufferedReader(file, DEFAULT_CHARSET)) {
			obj = p.parse(reader);
		}
		return obj;
	}
	
	public static DaTextObject createObjectFromStream(InputStream in) throws IOException, IllegalArgumentException{
		
		DefaultDaTextParser p = new DefaultDaTextParser();
		DaTextObject obj;
		try (Reader reader = new InputStreamReader(in, DEFAULT_CHARSET)) {
			obj = p.parse(reader);
		}
		return obj;
	}
	
	
	
}
