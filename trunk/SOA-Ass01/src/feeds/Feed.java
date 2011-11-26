package feeds;

import java.net.URL;
import java.util.Map;
import java.util.Vector;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;

public abstract class Feed {
	protected	URL					_url;

	public void setUrl(URL url) {
		this._url = url;
	}

	public URL getUrl() {
		return _url;
	}

	/**
	 * @param element					the name of the new feed
	 * @param address					the feed's address
	 * 
	 * @return 							the answer that should be delivered to the client
	 * 									(only when the operation succeeded)
	 * 
	 * @throws BadRequestException		in case the request is damaged
	 * @throws NotImplaementedException	in case the given element is actually a collection
	 */
	public abstract void putNamedFeed(Vector<String> requestPath, String address)
	throws BadRequestException, NotImplaementedException;

	/**
	 * @param feed					the feed\s (collection or element) that we want its content 
	 * @param filters				the requested feed filters
	 * 
	 * @return						the answer that should be delivered to the client
	 * 								(only when the operation succeeded)
	 * 
	 * @throws BadRequestException	in case the request is damaged
	 */
	public StringBuilder getFeeds(Vector<String> requestPath, Map<String,String> filters)
	throws BadRequestException
	{
		if(requestPath == null)
			throw new BadRequestException();
		return new StringBuilder();
	}
	
	/**
	 * @param collection				the collection that we want to add the feed to
	 * @param address					the feed's address
	 * 
	 * @return 							the answer that should be delivered to the client
	 * 									(only when the operation succeeded)
	 * 
	 * @throws BadRequestException		in case the request is damaged
	 * @throws NotImplaementedException	in case the given collection is actually an element
	 */
	public abstract void postUnnamedFeed(String collection, String address)
	throws BadRequestException, NotImplaementedException;
	
	/**
	 * @param feed						the element or collection that we want to delete
	 */
	public abstract void deleteFeeds(String feed)
		throws BadRequestException;



}
