package feeds;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;


//TODO: should use write-read-locks?.. 
// A: Yes we should. to support multi threaded fetching 

public class FeedCollection extends Feed{

	protected	Map<String,Feed>	_namedFeeds;
	protected	Vector<String>		_unnamedFeedsUrls;
	protected	int					_numOfFeeds;
	
	private FeedCollection(String feedName, URL url, Feed childFeed) {
		super(feedName,url);
		setNamedFeeds(new HashMap<String,Feed>());
		if(childFeed != null)
			_namedFeeds.put(childFeed.getName(), childFeed);
		setUnnamedFeedsUrls(new Vector<String>());
		//setNumOfFeeds(0);
	}
	
	public FeedCollection(String feedName, Feed childFeed) {
		super(feedName);
		setNamedFeeds(new HashMap<String,Feed>());
		if(childFeed != null)
			_namedFeeds.put(childFeed.getName(), childFeed);
		setUnnamedFeedsUrls(new Vector<String>());
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
	public StringBuilder getFeeds(String feed, Map<String,String> filters)
		throws BadRequestException{

		// TODO Auto-generated method stub
	
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
	public void postUnnamedFeed(String collection, String address)
		throws BadRequestException, NotImplaementedException {
		
		// TODO Auto-generated method stub
		
		return;
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
		throws BadRequestException, NotImplaementedException {
		Feed f = null;
		String nextElementName;
		String[] nextRequestPath; 
		
		if(requestPath.isEmpty())
			//throw exception cause PUT method is unsupported for Feed collections
			throw new BadRequestException();

		nextElementName = requestPath.remove(0);
		
		nextRequestPath = new String[requestPath.size()];
		requestPath.copyInto(nextRequestPath);
		
		f = this._namedFeeds.get(nextElementName);
		if(f == null)
		{
			f = FeedFactory.create(nextElementName, requestPath);
			this._namedFeeds.put(nextElementName, f);
		}
		f.putNamedFeed(new Vector<String>(Arrays.asList(nextRequestPath)), address);
	}

	/**
	 * @param feed						the element or collection that we want to delete
	 */
	public void deleteFeeds(String feed)
		throws BadRequestException {
		
		// TODO Auto-generated method stub
		
		return;
	}

	public void setNamedFeeds(Map<String,Feed> feeds) {
		this._namedFeeds = feeds;
	}

	public Map<String,Feed> getNamedFeeds() {
		return _namedFeeds;
	}

	public void setNumOfFeeds(int numOfFeeds) {
		this._numOfFeeds = numOfFeeds;
	}
	
	public void setUnnamedFeedsUrls(Vector<String> unnamedFeedsUrls) {
		this._unnamedFeedsUrls = unnamedFeedsUrls;
	}

	public Vector<String> getUnnamedFeedsUrls() {
		return _unnamedFeedsUrls;
	}
	
	public int getNumOfFeeds() {
		return _numOfFeeds;
	}
	
	public void increaseNumOfFeeds() {
		_numOfFeeds++;
	}
	
	public void decreaseNumOfFeeds() {
		_numOfFeeds--;
	}
}
