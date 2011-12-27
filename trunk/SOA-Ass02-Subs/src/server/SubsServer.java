package server;

import java.io.BufferedReader;
import java.io.IOException;

import org.subscription.news.*;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;

public class SubsServer {

	public PostCollectionResponse postCollection(PostCollectionRequest postCollectionRequest) {

		String name = postCollectionRequest.getName();

		String url = postCollectionRequest.getUrl();


			if(!request.getRequestURI().endsWith("/"))

				//throw exception => POST method is unsupported for Feed elements
				throw new NotImplaementedException();
			_feedHandler.postFeed(getRequestPath(request), content);

//		catch (NotImplaementedException e) {
//			response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
//			return;
//		}
//		catch (BadRequestException e) {
//			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}



		// TODO : fill this with the necessary business logic
		throw new java.lang.UnsupportedOperationException("Please implement "
				+ this.getClass().getName() + "#postCollection");

	}


	protected String getRequestContent(HttpServletRequest request) {

		StringBuffer sb = new StringBuffer();
		String line = null;

		try {

			BufferedReader reader = request.getReader();

			while ((line = reader.readLine()) != null)
				sb.append(line);
		}
		catch (Exception e){}

		return sb.toString();
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String content = getRequestContent(request);

		try
		{
			if(request.getRequestURI().endsWith("/"))
				//throw exception => PUT method is unsupported for Feed collections
				throw new NotImplaementedException();
			_feedHandler.putFeed(getRequestPath(request), content);
		}
		catch (NotImplaementedException e) {
			response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try{
			if(request.getRequestURI().endsWith("/"))
				_feedHandler.deleteCollectionFeeds(getRequestPath(request));
			else
				_feedHandler.deleteElementFeeds(getRequestPath(request));
		}
		catch (NotImplaementedException e) {
			response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}

}
