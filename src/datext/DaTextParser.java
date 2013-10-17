package datext;
// TODO: description/documentation

import java.io.*;
import java.nio.charset.Charset;

/**
 *
 * @author Christopher Collin Hall
 */
public abstract class DaTextParser {

	
	
	public abstract DaTextObject parse(Reader in);
	
	public DaTextObject parse(String input){
		StringReader sin = new StringReader(input);
		return parse(sin);
	}
	
	public DaTextObject parse(InputStream in, Charset cs){
		return parse(new InputStreamReader(in,cs));
	}
}
