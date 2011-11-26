package feeds;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;

public class Feed {
	protected	URL					_url;
	protected	Map<String,Feed>	_namedFeeds;
	protected	Vector<String>		_unnamedFeedsUrls;

	public Feed(Feed childFeed, String childFeedName) {
		setNamedFeeds(new HashMap<String,Feed>());
		if(childFeed != null)
			_namedFeeds.put(childFeedName, childFeed);
		setUnnamedFeedsUrls(new Vector<String>());
	}

	public Feed() {
		setNamedFeeds(new HashMap<String,Feed>());
		setUnnamedFeedsUrls(new Vector<String>());
	}

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
	public void putNamedFeed(Vector<String> requestPath, String address)
	throws BadRequestException, NotImplaementedException
	{
		Feed f = null;
		String nextElementName;
		String[] nextRequestPath; 
		
		try {
			if(requestPath.isEmpty())
			{
				setUrl(new URL(address));
				return;
			}
		} catch (Exception e) {
			throw new BadRequestException();
		}
		
		//assume requestPath.isEmpty()=false
		nextElementName = requestPath.remove(0);
		
		nextRequestPath = new String[requestPath.size()];
		requestPath.copyInto(nextRequestPath);
		
		f = this._namedFeeds.get(nextElementName);
		if(f == null)
		{
			f = FeedFactory.create(requestPath);
			this._namedFeeds.put(nextElementName, f);
		}
		f.putNamedFeed(new Vector<String>(Arrays.asList(nextRequestPath)), address);
	}

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
		StringBuilder sb = new StringBuilder();
		Feed nextFeed;
		
		if(requestPath.isEmpty())
		{
			for (Feed feed : _namedFeeds.values()) {
				//TODO threads
				sb.append(feed.getFeeds(requestPath, filters));
			}
			//TODO connection
			sb.append(getUrl()!=null? getUrl().toString():"null");
			//TODO unnamed feeds and what about FeedElement (inheritance??)
		}
		
		else
		{
			nextFeed = _namedFeeds.get(requestPath.remove(0));
			if(nextFeed != null)
				sb = nextFeed.getFeeds(requestPath, filters);
			else
				throw new BadRequestException();
		}
		
		return sb;
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
	public void postUnnamedFeed(String collection, String address)
	throws BadRequestException, NotImplaementedException{}
	
	/**
	 * @param feed						the element or collection that we want to delete
	 */
	public void deleteFeeds(String feed)
		throws BadRequestException{}

	public void setNamedFeeds(Map<String,Feed> feeds) {
		this._namedFeeds = feeds;
	}

	public Map<String,Feed> getNamedFeeds() {
		return _namedFeeds;
	}

	public void setUnnamedFeedsUrls(Vector<String> unnamedFeedsUrls) {
		this._unnamedFeedsUrls = unnamedFeedsUrls;
	}

	public Vector<String> getUnnamedFeedsUrls() {
		return _unnamedFeedsUrls;
	}
	


}
