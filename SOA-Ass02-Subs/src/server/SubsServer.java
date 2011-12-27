package server;

import java.io.BufferedReader;
import java.io.IOException;
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
				// throw new NotImplaementedException();

				_feedHandler.postFeed(getRequestPath(name), url);
		}
		catch (BadRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NotImplaementedException e) {
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



	@Override
	protected void doDelete(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			if (request.getRequestURI().endsWith("/"))
				_feedHandler.deleteCollectionFeeds(getRequestPath(request));
			else
				_feedHandler.deleteElementFeeds(getRequestPath(request));
		} catch (NotImplaementedException e) {
			response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}

	public DeleteElementResponse deleteElement(
			DeleteElementRequest deleteElementRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public URLsList getURLs(GetURLsRequest getURLsRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public DeleteCollectionResponse deleteCollection(
			DeleteCollectionRequest deleteCollectionRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}
