package server;

import java.net.URL;
import java.util.Arrays;
import java.util.Vector;

import org.subscription.news.*;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;
import feeds.FeedHandler;

public class SubsServer {

	protected FeedHandler _feedHandler;

	public SubsServer() {

		_feedHandler = new FeedHandler();
	}

	public PostCollectionResponse postCollection(
			PostCollectionRequest postCollectionRequest) {

		String name = postCollectionRequest.getName();

		String url = postCollectionRequest.getUrl();

		try {

			if (!name.endsWith("/"))

				// throw exception => POST method is unsupported for Feed
				// elements
				throw new NotImplaementedException();

			_feedHandler.postFeed(getRequestPath(name), url);
		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotImplaementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PostCollectionResponse answer = new PostCollectionResponse();
		answer.setPostCollectionResponse(name);

		return answer;
	}

	private Vector<String> getRequestPath(String name)
			throws NotImplaementedException, BadRequestException {

		try {

			String[] pathArray = name.split("/");

			// TODO: IF THERE IS A BUG.. START HERE..
			if (pathArray[0].isEmpty())
				return new Vector<String>(Arrays.asList(pathArray).subList(1,
						pathArray.length));
		} catch (Exception e) {
			throw new BadRequestException();
		}
		throw new BadRequestException();
	}

	public PutElementResponse putElement(PutElementRequest putElementRequest) {

		String name = putElementRequest.getName();

		String url = putElementRequest.getUrl();
		try {

			if (name.endsWith("/"))
				// throw exception => PUT method is unsupported for Feed
				// collections
				throw new NotImplaementedException();

			_feedHandler.putFeed(getRequestPath(name), url);

		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotImplaementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PutElementResponse answer = new PutElementResponse();
		answer.setPutElementResponse(name);

		return answer;
	}

	public DeleteElementResponse deleteElement(
			DeleteElementRequest deleteElementRequest) {

		String name = deleteElementRequest.getName();

		try {

			if (name.endsWith("/"))
				throw new NotImplaementedException();
			else
				_feedHandler.deleteElementFeeds(getRequestPath(name));
		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotImplaementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DeleteElementResponse answer = new DeleteElementResponse();
		answer.setDeleteElementResponse("DONE");

		return answer;
	}

	public DeleteCollectionResponse deleteCollection(
			DeleteCollectionRequest deleteCollectionRequest) {

		String name = deleteCollectionRequest.getName();

		try {

			if (name.endsWith("/"))
				_feedHandler.deleteCollectionFeeds(getRequestPath(name));
			else
				throw new NotImplaementedException();
		} catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotImplaementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DeleteCollectionResponse answer = new DeleteCollectionResponse();
		answer.setDeleteCollectionResponse("DONE");

		return answer;
	}

	public URLsList getURLs(GetURLsRequest getURLsRequest) {

		String name = getURLsRequest.getIdentifier();

		Vector<URL> urlsVec = null;

		try {
			urlsVec = name.endsWith("/") ?
					_feedHandler.getFeedsCollection(getRequestPath(name)) :
						_feedHandler.getFeedsElement(getRequestPath(name));
		}
		catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NotImplaementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		URLsList urls = new URLsList();

		urls.setURL((String[]) urlsVec.toArray());

		return urls;
	}
}
