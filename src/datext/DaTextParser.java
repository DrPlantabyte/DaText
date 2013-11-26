package datext;
// TODO: description/documentation

import java.io.*;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 *
 * @author Christopher Collin Hall
 */
public abstract class DaTextParser {

	// TODO better documentation
	/**
	 * <p/>
	 * To preserve thread-safety, implementations must not use non-final 
	 * member variables. Typically this means using a nested class that is 
	 * instantiated to handle the actual parsing in each invocation of this 
	 * method.
	 * @param in
	 * @return
	 * @throws IOException 
	 */
	public abstract DaTextObject parse(Reader in, Locale locale) throws IOException;
	
	public DaTextObject parse(String input, Locale locale) throws IOException{
		StringReader sin = new StringReader(input);
		return parse(sin,locale);
	}
	
	public DaTextObject parse(InputStream in, Charset cs, Locale locale) throws IOException{
		return parse(new InputStreamReader(in,cs),locale);
	}
}
