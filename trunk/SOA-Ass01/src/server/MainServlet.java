package server;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;
import feeds.FeedCollection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Vector;

public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = -8430957508258720026L;
	
//	Method	Collection										Element
//	======	==========										=======
//	PUT		Unsupported										Set the URL of a named feed
//	POST	Add a feed URL to a collection					Unsupported
//	DELETE	Remove entire collection						Remove a specific feed
//	GET		Return items from all feeds in the collection	Return items from this feed
	
//	200	OK
//	501	Not	implemented	response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
//	400	Bad request		response.sendError(HttpServletResponse.SC_BAD_REQUEST);
	
	protected FeedCollection _fc;
	
	public MainServlet(FeedCollection fc) {

		setFc(fc);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		StringBuilder answer = new StringBuilder();
		
		try{
			
			answer = _fc.getFeeds(request.getQueryString(), request.getParameterMap());
		}
		catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(answer.toString());
		out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String content = getRequestContent(request);
		
		try{
			
			_fc.postUnnamedFeed(request.getQueryString(), content);
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
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String content = getRequestContent(request);
		
		try{
			String [] pathArray = request.getRequestURI().split("/");
			if(pathArray[0].isEmpty() && pathArray[1].equalsIgnoreCase("ex1"))
				_fc.putNamedFeed(new Vector<String>(Arrays.asList(pathArray).subList(2, pathArray.length)), content);
			else throw new BadRequestException();
		}
		catch (NotImplaementedException e) {
			response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
			return;
		}
		catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try{
			
			_fc.deleteFeeds(request.getQueryString());
		}
		catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
	}
	
	protected String getRequestContent(HttpServletRequest request) {

		StringBuffer sb = new StringBuffer();
		String line = null;

		try {

			BufferedReader reader = request.getReader();

			while ((line = reader.readLine()) != null)
				sb.append(line);
		}
		catch (Exception e){
			//TODO Auto-generated
		}

		return sb.toString();
	}

	public void setFc(FeedCollection fc) {
		this._fc = fc;
	}

	public FeedCollection getFc() {
		return this._fc;
	}
}
