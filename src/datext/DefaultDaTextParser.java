/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext;

/**
 * Uses a FSM to parse a character stream to a DaText tree.
 * @author  Christopher Collin Hall
 */
public class DefaultDaTextParser extends DaTextParser{
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
	/** FSM state */
	ReadState state = ReadState.WHITESPACE; // starting FSM state
	
	StreamHandler input;
	/**
	 * In the interest of being thread-safe, all constructors are 
	 * private. Parsing is done through static methods that create a 
	 * unique instance of all variables for each invokation.
	 * @param streamReader The input character stream to parse.
	 */
	private DefaultDaTextParser(java.io.InputStreamReader streamReader){
		input = new StreamHandler(streamReader);
	}
	
	public static DaTextElement parse(java.io.InputStreamReader streamReader){
		
	}
}
