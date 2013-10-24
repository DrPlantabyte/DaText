/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datext.io;

import java.io.IOException;
import java.io.Reader;

/**
 * This class is used for thread safety. It keeps together variables that are
 * shared between methods but not between input streams. This is the real
 * implementation of the parser
 * @author  Christopher Collin Hall
 */
public class StreamHandler {

	final Reader in;
	/**
	 * set to true after first read
	 */
	boolean streamStarted = false;
	/**
	 * next character in stream
	 */
	int future = -1;
	/**
	 * Currently read character
	 */
	int current = -1;

	/**
	 * this is the only constructor
	 */
	public StreamHandler(Reader instream) {
		in = instream;
	}
	/**
	 * Closes the underlying stream reader.
	 * @throws IOException Thrown if there was a problem closing the stream.
	 */
	public void close() throws IOException{
		in.close();
	}

	/**
	 * Reads another character from the stream.
	 *
	 * @return Returns the next character in the stream, or null if the stream
	 * has reached its end.
	 * @throws IOException Thrown if there is an error reading the input stream
	 * reader.
	 */
	public Character readNextChar() throws IOException {
		if (streamStarted == false) {
			streamStarted = true;
			current = in.read();
			future = in.read();
		} else {
			current = future;
			do {
				future = in.read(); // Skip the \r character (use only \n)
			} while ((char) future == '\r');
		}
		if (current < 0) {
			return null;
		} else {
			return (char) current;
		}
	}

	/**
	 * Returns the current character in the stream without reading from the
	 * stream.
	 *
	 * @return Returns the current character in the stream, or null if the
	 * stream has reached its end.
	 */
	public Character peekCurrent() {
		if (current < 0) {
			return null;
		}
		return (char) current;
	}

	/**
	 * Returns the next character in the stream without reading from the stream.
	 *
	 * @return Returns the next character in the stream, or null if the stream
	 * has reached its end.
	 */
	public Character peekNext() {
		if (future < 0) {
			return null;
		}
		return (char) future;
	}

	/**
	 * reads the stream until a non-whitespace character appears
	 */
	public void skipWhiteSpace() throws IOException {
		while (current >= 0 && Character.isWhitespace(current) == true) {
			readNextChar();
		}
	}

	/**
	 * Parses a DaText compliant string from the input stream.
	 *
	 * @return
	 */
	String readText() throws IOException, IllegalArgumentException {
		StringBuilder sb = new StringBuilder();
		skipWhiteSpace();
		Character c = readNextChar();
		char quoteChar = '"';
		boolean inQuote = false;
		int lastQuote = 0;
		scan:
		{
			while (c != null && c.equals('\n') == false) {
				if (c.equals('\\')) {
					// character escape
					c = readNextChar();
					if (c == null) {
						throw new IllegalArgumentException("Found \\ at end of stream, escaped character is required after \\");
					}
					sb.append(c);
				} else if (inQuote == false && (c.equals('"') || c.equals('\''))) {
					// start of quote
					quoteChar = c;
					inQuote = true;
				} else if (inQuote == true && c.equals(quoteChar)) {
					// end of quote
					inQuote = false;
					lastQuote = sb.length();
				} else if (inQuote == false && c.equals('/') && peekNext().equals('*')) {
					// block comment
					do {
						readNextChar();
					} while ((peekCurrent().equals('*') && peekNext().equals('/')) == false);
					readNextChar();
				} else if (inQuote == false && c.equals('/') && peekNext().equals('/')) {
					// line comment
					do {
						readNextChar();
					} while ((peekCurrent().equals('\n')) == false);
					break scan;
				} else {
					// append character to StringBuilder
					sb.append(c);
				}
				c = readNextChar();
			}
		}

		// trim off trailing whitespace (unless quoted)
		int ending = sb.length() - 1;
		while (ending >= 0 && Character.isWhitespace(sb.charAt(ending)) && ending >= lastQuote) {
			ending--;
		}
		return sb.substring(0, ending + 1);
	}

	String readLine() throws IOException {
		if (peekCurrent() == null) {
			return null; // end of stream
		}
		StringBuilder sb = new StringBuilder();
		Character c = readNextChar();
		while (c != null && c.equals('\n') == false) {
			sb.append(c);
			c = readNextChar();
		}
		return sb.toString();
	}

	public Character readNextCharWtihEscape() throws IOException {
		Character c = readNextChar();
		if(c == '\\'){
			return readNextChar();
		}
		return c;
	}
}
