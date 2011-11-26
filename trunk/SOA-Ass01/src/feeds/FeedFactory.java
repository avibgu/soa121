package feeds;

import java.net.URL;
import java.util.Vector;

public class FeedFactory {

	public static Feed create(String feedName, Vector<String> requestPath) {
		String NextElementName;
		
		if(requestPath.isEmpty())
			return new Feed(feedName);
		NextElementName = requestPath.remove(0);
		return new FeedCollection(feedName,create(NextElementName,requestPath));
	}
}
