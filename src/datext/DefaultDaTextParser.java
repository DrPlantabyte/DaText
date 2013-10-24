/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

import datext.io.StreamHandler;
import java.io.IOException;
import java.io.Reader;

/**
 * Uses a FSM to parse a character stream to a DaText tree.
 * @author  Christopher Collin Hall
 */
public class DefaultDaTextParser extends DaTextParser{

	
	
	@Override
	public DaTextObject parse(Reader in) throws IOException {
		Parser p = new Parser(in);
		p.parse();
		return p.toDaTextObject();
	}
	
	
	
	
	/** These are the states of the FSM for parsing the file */
	protected enum ReadState {
		/** between &#47;* and *&#47; */
		MULTILINE_COMMENT,
		/** between &#47;&#47; and the end of the line */
		LINE_COMMENT,
		/** between # and the end of the line */
		ANNOTATION,
		/** reading a keyname */
		KEY,
		/** skipping whitespace */
		WHITESPACE,
		/** reading a quoted string */
		QUOTE,
		/** reading a quoted string */
		SEMIQUOTE,
		/** reading an object */
		CURLY_BRACKET,
		/** reading a list */
		SQUARE_BRACKET
	}
	
	/**
	 * This class does the heavy-lifting for the parsing. The FSM actually 
	 * resides inside this class to avoid thread problems.
	 */
	protected class Parser {

		/**
		 * FSM state
		 */
		ReadState state = ReadState.WHITESPACE; // starting FSM state
		/** input stream */
		final StreamHandler input;
		
		Parser(Reader r) {
			input = new StreamHandler(r);
		}

		void parse() throws IOException {
			while(input.peekCurrent() != null){
				Character c = input.readNextCharWtihEscape();
				if(c == null) break; // we're done (EOF)
				switch(state){
					// Finite state machine (FSM)
					case WHITESPACE:
						// skip whitespace;
						if(!Character.isWhitespace(c)){
							if(c == '')
						}
						break;
					default:
						// Injection of additional enums?!
						throw new UnsupportedOperationException("WTF!!! Where did '" + state.name() + "' come from!");
				}
			}
			throw new UnsupportedOperationException("Not yet implemented");
		}
		

		DaTextObject toDaTextObject() {
			throw new UnsupportedOperationException("Not yet implemented");
		}

		
		
	}
	
}
