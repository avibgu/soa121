package feeds;

import java.util.Vector;

public class FeedFactory {

	public static Feed create(Vector<String> requestPath) {
		String NextElementName;
		
		if(requestPath == null || requestPath.isEmpty())
			return new Feed();
		NextElementName = requestPath.remove(0);
		return new Feed(create(requestPath),NextElementName);
	}
}
