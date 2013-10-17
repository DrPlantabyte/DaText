package datext;
// TODO: description/documentation

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 *
 * @author Christopher Collin Hall
 */
public abstract class DaTextParser {

	public abstract DaTextObject parse(InputStream in, Charset cs);
}
