package feeds;

import java.net.URL;
import java.util.Vector;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;

public class FeedHandler {
	
	protected Feed _mainFeed;

	/*
	 * Constructor
	 */
	public FeedHandler() {
		setMainFeed(new Feed());
	}

	public void setMainFeed(Feed f) {
		this._mainFeed = f;
	}

	public Feed getMainFeed() {
		return this._mainFeed;
	}

	/**
	 * @param requestPath				the request Path that left to the target feed
	 * @param address					the feed's address to put
	 * 
	 * @throws BadRequestException		in case the request is damaged
	 * @throws NotImplaementedException	in case the given path is a collection
	 */
	public void putFeed(Vector<String> requestPath, String address)
	throws BadRequestException, NotImplaementedException
	{
		try {
			findFeed(requestPath, true).setUrl(new URL(address));
		} catch (Exception e) {//catch any IO exceptions and return Bad Request response
			throw new BadRequestException();
		}
	}

	/**
	 * 
	 * @param requestPath The path to the target feed
	 * @param create Flag to indicate if the function should create the feed when it ain't found
	 * @return the feed object which found (or the new one - when created)
	 * @throws BadRequestException When feed not found and create flag is false
	 */
	private Feed findFeed(Vector<String> requestPath, boolean create)
			throws BadRequestException {
		Feed f = _mainFeed;
		Feed newF = null;
		String feedName;
		
		if(requestPath.isEmpty())
			return f;
	
		//assume requestPath.isEmpty()=false
		while (!requestPath.isEmpty()) {
			feedName = requestPath.remove(0);
			
			System.out.println("findFeed: " + feedName);
			
			newF = f.getNamedFeeds().get(feedName);
			if(newF == null)
			{
				if(create)
					f = create(requestPath,f,feedName);
				else
					throw new BadRequestException();
			}
			else
				f =newF ;
		}
			
		return f;
	}

	/**
	 * @param requestPath				the collection that we want to add the feed to
	 * @param address					the feed's address
	 * 
	 * @throws BadRequestException		in case the request is damaged
	 * @throws NotImplaementedException	in case the given request is an element
	 */
	public void postFeed(Vector<String> requestPath, String address)
	throws BadRequestException, NotImplaementedException{
		try {
			findFeed(requestPath, true).addUnnamedFeedURL(new URL(address));
		} catch (Exception e) {//catch any IO exceptions and return Bad Request response
			throw new BadRequestException();
		}
	}

	/**
	 * recursive function to create new Feed in specific location
	 * @param requestPath The location where the feed should placed 
	 * @param fOld The parent feed of the new feed
	 * @param feedName The name of the new feed
	 * @return New feed
	 */
	public Feed create(Vector<String> requestPath, Feed fOld, String feedName) {
		String NextFeedName;
		Feed newFeed = new Feed();
		
		fOld.addNamedFeed(newFeed,feedName);
		if(requestPath.isEmpty())
			return newFeed;
		NextFeedName = requestPath.remove(0);
		return create(requestPath,newFeed,NextFeedName);
	}

	/**
	 * @param requestPath			the feed path (collection only) that we want its content 

	 * @return						Vector of URLs of the given feed which later be fetched for RSS content
	 * @throws BadRequestException	in case the request is damaged
	 */
	public Vector<URL> getFeedsCollection(Vector<String> requestPath)throws BadRequestException
	{
		return findFeed(requestPath, false).getAllUrls();
	}

	/**
	 * @param requestPath			the feed path (element only) that we want its content 

	 * @return						Vector of URLs of the given feed which later be fetched for RSS content
	 * @throws BadRequestException	in case the request is damaged
	 */
	public Vector<URL> getFeedsElement(Vector<String> requestPath) throws BadRequestException
	{
		Feed ans = new Feed();
		
		try {
			ans = findFeed(requestPath, false);
		}
		catch (BadRequestException e) {
			// TODO Auto-generated catch block
			System.out.println("BadRequestException - getFeedsElement");
//			e.printStackTrace();
			throw e;
		}
		
		return ans.getElementsUrls();
	}

	/**
	 * 
	 * @param requestPath The path of the requested feed (collection only) 
	 * @throws BadRequestException In case the requested path damaged
	 */
	public void deleteCollectionFeeds(Vector<String> requestPath)
		throws BadRequestException
		{
			try {
				findFeed(requestPath, false).deleteAll();
			} catch (Exception e) {//catch any IO exceptions and return Bad Request response
				throw new BadRequestException();
			}
	}

	/**
	 * @param requestPath						the element that we want to delete
	 * @throws BadRequestException In case the requested path damaged
	 */
	public void deleteElementFeeds(Vector<String> requestPath) 
		throws BadRequestException
		{
			try {
				findFeed(requestPath, false).deleteElements();
			} catch (Exception e) {//catch any IO exceptions and return Bad Request response
				throw new BadRequestException();
			}
	}
}
