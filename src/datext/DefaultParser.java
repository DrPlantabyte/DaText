/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import java.io.IOException;
import java.io.Reader;

/**
 *
 * @author Christopher Collin Hall
 */
public class DefaultParser extends DaTextParser {

	
	
	
	@Override
	public DaTextObject parse(Reader in) throws IOException {
		ParserHelper p = new ParserHelper(in);
		return p.parse();
	}
	
	/** 
	 * States used in the parser's FSM
	 */
	private enum State {
		SKIP_WHITESPACE;
	}
	/**
	 * Does the actual heavy-lifting for parsing the stream
	 */
	private class ParserHelper{
		final Reader stream;
		
		State state;
		ParserHelper(Reader in){
			stream = in;
			state = State.SKIP_WHITESPACE;
		}
		
		DaTextObject parse() throws IOException{
			try{
				// TODO: read the stream
				throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
			}finally{
				stream.close();
			}
			
		}
	}
}
