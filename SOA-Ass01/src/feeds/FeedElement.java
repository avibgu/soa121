package feeds;

import java.net.URL;
import java.util.Map;
import java.util.Vector;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;

public class FeedElement extends Feed{

	private FeedElement(){}
	
	public static Feed createFeedElement(){
		return new FeedElement();
	}

	@Override
	public void putNamedFeed(Vector<String> requestPath, String address) throws BadRequestException, NotImplaementedException{
		try {
			if(!requestPath.isEmpty())
				throw new BadRequestException();
			setUrl(new URL(address));
		} catch (Exception e) {
			throw new BadRequestException();
		}
	}

	@Override
	public StringBuilder getFeeds(Vector<String> requestPath, Map<String, String> filters)
			throws BadRequestException {
		StringBuilder sb = super.getFeeds(requestPath,filters);

		if(requestPath.isEmpty())
		{
			//TODO connection
			sb.append(getUrl().toString());
			//TODO unnamed feeds and what about FeedElement (inheritance??)
		}
		
		else
			throw new BadRequestException();
		
		return sb;
	}

	@Override
	public void postUnnamedFeed(String collection, String address)
			throws BadRequestException, NotImplaementedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFeeds(String feed) throws BadRequestException {
		// TODO Auto-generated method stub
		
	}

}
