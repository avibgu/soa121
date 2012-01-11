package server;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import xml.DOMStreamReader;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;
import feeds.FeedHandler;
import filter.RSSAtomFilter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
	
	protected FeedHandler _feedHandler;
	
	public MainServlet(FeedHandler fh) {

		setFeedHandler(fh);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		
		responseWithTheMainHtmlFile(response.getWriter());

//		
//		try {
//			
//			Vector<URL> urls = request.getRequestURI().endsWith("/")? 
//						_feedHandler.getFeedsCollection(getRequestPath(request)) : _feedHandler.getFeedsElement(getRequestPath(request)); 
//			
//			ArrayList<Node> fetchedFeeds = fetchFeeds(urls, request.getParameterMap());
//			
//			response.setCharacterEncoding("UTF-8");
//			
//			sendResultDocumentToCaller(createDocumentFromFeeds(fetchedFeeds), response.getWriter());
//		}
//		catch (NotImplaementedException e) {
//			response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
//			return;
//		}
//		catch (BadRequestException e) {
//			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
//			return;
//		}
	}

	private void responseWithTheMainHtmlFile(PrintWriter printWriter) {

		File file = new File("html/container-app.html");

		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;

		try {

			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);

			while (dis.available() != 0)
				printWriter.println(dis.readLine());
		      
			printWriter.flush();

			fis.close();
			bis.close();
			dis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Node> fetchFeeds(Vector<URL> urls, Map<String,String[]> filters){
		
		ArrayList<Node> fetchedFeeds = new ArrayList<Node>();
		
		// Create an executor:
        ExecutorService e = Executors.newFixedThreadPool(4);

        for (URL url: urls)
        	e.execute(new DOMStreamReader(url, fetchedFeeds, filters));

        // this causes the executor not to accept any more
        //tasks, and to kill all of its threads when all the
        //submitted tasks are done.
        e.shutdown();
        
        try {
			while (!e.awaitTermination(3, TimeUnit.SECONDS)) continue;
		}
        catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
        return fetchedFeeds;
	}
	
	private Document createDocumentFromFeeds(ArrayList<Node> fetchedFeeds) {

		if(null == fetchedFeeds) return null;
		
		Document document = null;
		
		try {

			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			document = b.newDocument();
			
			Element rss = document.createElement("rss"); 
			document.appendChild(rss);
			
			Element channel = document.createElement("channel");
			rss.appendChild(channel);
			
			Element title = document.createElement("title");
			title.appendChild(document.createTextNode("Feeds Result"));
			channel.appendChild(title);
			
			RSSAtomFilter itemFilter = new RSSAtomFilter("item");
			
			NodeIterator iter;
			
			Node item;
			
			for (Node feed: fetchedFeeds){
				
				iter = ((DocumentTraversal)feed).createNodeIterator(
						feed, NodeFilter.SHOW_ELEMENT, itemFilter, false);

				while ((item = iter.nextNode()) != null)
					channel.appendChild(document.importNode(item, true));
			}
			
		}
		catch (ParserConfigurationException e) {
			System.out.println(e.toString()); 	
		}
		
		return document;
	}

	private void sendResultDocumentToCaller(Document doc, PrintWriter out) {

		Transformer transformer = null;
        
		while (true){
			
			try {

				transformer = TransformerFactory.newInstance().newTransformer();
				break;
			}
			catch (TransformerConfigurationException e2) {}
		}
		
    	Source s = new DOMSource(doc);
		Result r = new StreamResult(out);

		int numOfTries = 3;
		
		while (numOfTries > 0){
			
			try {
				
				transformer.transform(s, r);
				break;
			}
			catch (TransformerException e3) {
			}
			finally{
				numOfTries--;
			}
		}
	
		out.close();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String content = getRequestContent(request);
		
		try
		{
			if(!request.getRequestURI().endsWith("/"))
				//throw exception => POST method is unsupported for Feed elements
				throw new NotImplaementedException();
			_feedHandler.postFeed(getRequestPath(request), content);
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

	private Vector<String> getRequestPath(HttpServletRequest request) throws NotImplaementedException, BadRequestException {
		try {
			String [] pathArray = request.getRequestURI().split("/");
			if(pathArray[0].isEmpty() && pathArray[1].equalsIgnoreCase("ex1"))
				return new Vector<String>(Arrays.asList(pathArray).subList(2, pathArray.length));
		} catch (Exception e) {
			throw new BadRequestException();
		}
		throw new BadRequestException();
	}

	public void setFeedHandler(FeedHandler fh) {
		this._feedHandler = fh;
	}

	public FeedHandler getFeedHandler() {
		return this._feedHandler;
	}
}
