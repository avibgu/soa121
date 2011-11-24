package feeds;
import java.util.Map;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;


//TODO: should use write-read-locks..

public class FeedsManager {

	public FeedsManager() {
	
		// TODO Auto-generated constructor stub
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
	public void putNamedFeed(String elementName, String address)
		throws BadRequestException, NotImplaementedException {
		
		// TODO Auto-generated method stub
		
		return;
	}

	/**
	 * @param feed						the element or collection that we want to delete
	 */
	public void deleteFeeds(String feed)
		throws BadRequestException {
		
		// TODO Auto-generated method stub
		
		return;
	}

	public int getNumOfFeeds() {
		
		// TODO Auto-generated method stub
		
		return 0;
	}
}
