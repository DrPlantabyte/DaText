package datext;
// TODO: description/documentation

import java.io.*;
import java.nio.charset.Charset;

/**
 *
 * @author Christopher Collin Hall
 */
public abstract class DaTextParser {

	
	/**
	 * To preserve thread-safety, implementations must not use non-final 
	 * member variables.
	 * @param in
	 * @return
	 * @throws IOException 
	 */
	public abstract DaTextObject parse(Reader in) throws IOException;
	
	public DaTextObject parse(String input) throws IOException{
		StringReader sin = new StringReader(input);
		return parse(sin);
	}
	
	public DaTextObject parse(InputStream in, Charset cs) throws IOException{
		return parse(new InputStreamReader(in,cs));
	}
}
