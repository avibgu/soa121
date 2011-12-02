package feeds;

import java.net.URL;
import java.util.Vector;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;

public class FeedHandler {
	
	protected Feed _mainFeed;
	
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
	 * @param element					the name of the new feed
	 * @param address					the feed's address
	 * 
	 * @return 							the answer that should be delivered to the client
	 * 									(only when the operation succeeded)
	 * 
	 * @throws BadRequestException		in case the request is damaged
	 * @throws NotImplaementedException	in case the given element is actually a collection
	 */
	public void putFeed(Vector<String> requestPath, String address)
	throws BadRequestException, NotImplaementedException
	{
		try {
			findFeed(requestPath, true).setUrl(new URL(address));
		} catch (Exception e) {
			throw new BadRequestException();
		}
	}

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
	 * @return 							the answer that should be delivered to the client
	 * 									(only when the operation succeeded)
	 * 
	 * @throws BadRequestException		in case the request is damaged
	 * @throws NotImplaementedException	in case the given collection is actually an element
	 */
	public void postFeed(Vector<String> requestPath, String address)
	throws BadRequestException, NotImplaementedException{
		try {
			findFeed(requestPath, true).addUnnamedFeedURL(new URL(address));
		} catch (Exception e) {
			throw new BadRequestException();
		}
	}
	
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
	 * @param feed					the feed\s (collection or element) that we want its content 
	 * @param urls 
	 * 
	 * @return						the answer that should be delivered to the client
	 * 								(only when the operation succeeded)
	 * 
	 * @throws BadRequestException	in case the request is damaged
	 */
	public Vector<URL> getFeedsCollection(Vector<String> requestPath)throws BadRequestException
	{
		return findFeed(requestPath, false).getAllUrls();
	}

	public Vector<URL> getFeedsElement(Vector<String> requestPath) throws BadRequestException
	{
		return findFeed(requestPath, false).getElementsUrls();
	}

	public void deleteCollectionFeeds(Vector<String> requestPath)
		throws BadRequestException
		{
			try {
				findFeed(requestPath, false).deleteAll();
			} catch (Exception e) {
				throw new BadRequestException();
			}
	}

	/**
	 * @param requestPath						the element or collection that we want to delete
	 */
	public void deleteElementFeeds(Vector<String> requestPath) 
		throws BadRequestException
		{
			try {
				findFeed(requestPath, false).deleteElements();
			} catch (Exception e) {
				throw new BadRequestException();
			}
	}
}
