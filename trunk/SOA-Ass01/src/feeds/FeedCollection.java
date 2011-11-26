package feeds;

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
	
	private FeedCollection(Feed childFeed, String childFeedName) {
		setNamedFeeds(new HashMap<String,Feed>());
		if(childFeed != null)
			_namedFeeds.put(childFeedName, childFeed);
		setUnnamedFeedsUrls(new Vector<String>());
	}
	
	public static Feed createFeedCollection(Feed childFeed, String childFeedName){
		return new FeedCollection(childFeed,childFeedName);
	}

	@Override
	public StringBuilder getFeeds(Vector<String> requestPath, Map<String,String> filters)
		throws BadRequestException{
		StringBuilder sb = super.getFeeds(requestPath,filters);
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

	@Override
	public void postUnnamedFeed(String collection, String address)
		throws BadRequestException, NotImplaementedException {
		
		// TODO Auto-generated method stub
		
		return;
	}

	@Override
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
			f = FeedFactory.create(requestPath);
			this._namedFeeds.put(nextElementName, f);
		}
		f.putNamedFeed(new Vector<String>(Arrays.asList(nextRequestPath)), address);
	}

	@Override
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
