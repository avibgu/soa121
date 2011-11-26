package feeds;

import java.util.Vector;

public class FeedFactory {

	public static Feed create(Vector<String> requestPath) {
		String NextElementName;
		
		if(requestPath == null || requestPath.isEmpty())
			return FeedElement.createFeedElement();
		NextElementName = requestPath.remove(0);
		return FeedCollection.createFeedCollection(create(requestPath),NextElementName);
	}
	
	public static Feed createFeedCollection() {
		return FeedCollection.createFeedCollection(null,null);
	}
}
