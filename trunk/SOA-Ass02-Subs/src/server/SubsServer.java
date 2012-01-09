package server;

import java.net.URL;
import java.util.Arrays;
import java.util.Vector;

import org.subscription.news.*;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;
import feeds.FeedHandler;

public class SubsServer {

	public static FeedHandler _feedHandler = new FeedHandler();

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
			System.out.println("BadRequestException - postCollection");
		} catch (NotImplaementedException e) {
			System.out.println("NotImplaementedException - postCollection");
		} catch (Exception e) {
			System.out.println("Exception - postCollection");
		}

		PostCollectionResponse answer = new PostCollectionResponse();
		answer.setPostCollectionResponse(name);

		return answer;
	}

	private Vector<String> getRequestPath(String name)
			throws NotImplaementedException, BadRequestException {

		try {

			String[] pathArray = name.split("/");

			if (pathArray[0].isEmpty())
				return new Vector<String>(Arrays.asList(pathArray).subList(1,
						pathArray.length));
		} catch (Exception e) {
			System.out.println("BadRequestException - getRequestPath");
			throw new BadRequestException();
		}
		throw new BadRequestException();
	}

	public PutElementResponse putElement(PutElementRequest putElementRequest) {

		String name = putElementRequest.getName();

		String url = putElementRequest.getUrl();

		System.out.println("putElement: " + name + ", " + url);

		try {

			if (name.endsWith("/"))
				// throw exception => PUT method is unsupported for Feed
				// collections
				throw new NotImplaementedException();

			_feedHandler.putFeed(getRequestPath(name), url);

		} catch (BadRequestException e) {
			System.out.println("BadRequestException - putElement");
		} catch (NotImplaementedException e) {
			System.out.println("NotImplaementedException - putElement");
		} catch (Exception e) {
			System.out.println("Exception - putElement");
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
			System.out.println("BadRequestException - deleteElement");
		} catch (NotImplaementedException e) {
			System.out.println("NotImplaementedException - deleteElement");
		} catch (Exception e) {
			System.out.println("Exception - deleteElement");
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
			System.out.println("BadRequestException - deleteCollection");
		} catch (NotImplaementedException e) {
			System.out.println("NotImplaementedException - deleteCollection");
		} catch (Exception e) {
			System.out.println("Exception - deleteCollection");
		}

		DeleteCollectionResponse answer = new DeleteCollectionResponse();
		answer.setDeleteCollectionResponse("DONE");

		return answer;
	}

	public URLsList getURLs(GetURLsRequest getURLsRequest) {

		String name = getURLsRequest.getIdentifier();

		Vector<URL> urlsVec = null;

		try {

			System.out.println("getURLs: " + name + ", " + getRequestPath(name));

			urlsVec = name.endsWith("/") ?
					_feedHandler.getFeedsCollection(getRequestPath(name)) :
						_feedHandler.getFeedsElement(getRequestPath(name));
		}
		catch (BadRequestException e) {
			System.out.println("BadRequestException - getURLs");
		}
		catch (NotImplaementedException e) {
			System.out.println("NotImplaementedException - getURLs");
		}
		catch (Exception e) {
			System.out.println("Exception - getURLs");
		}

		URLsList urls = new URLsList();

		try {
			if (urlsVec != null)
			{
				Object[] Object_Array = urlsVec.toArray();
				String[] String_Array = new String[Object_Array.length];

				for (int i=0;i<String_Array.length;i++)
					String_Array[i]=Object_Array[i].toString();

				urls.setURL((String_Array.length != 0) ? String_Array : new String[]{""});
			}

			else
				urls.setURL(new String[]{""});
		} catch (Exception e) {
			urls.setURL(new String[]{""});
		}

		return urls;
	}
}
