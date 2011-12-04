package server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.*;

import org.w3c.dom.Node;

import xml.DOMStreamReader;

import feeds.FeedHandler;

public class Main {
	
	public static final int		SERVER_PORT			= 8994;
	public static final String	SERVER_NAME			= "soa1";
	public static final String	FULL_SERVER_NAME	= "http://soa1.cs.bgu.ac.il:8994/ex1";

	public static void main(String[] args) {
			
		startServer();

//		testDOM();
	}
		
	public static void startServer(){
		
		Server server = new Server(SERVER_PORT);

		ServletContextHandler ctx = 
			new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		ctx.setContextPath("/ex1");
		ctx.addServlet(new ServletHolder(new MainServlet(new FeedHandler())), "/*");
		
		HandlerList list = new HandlerList();
		
		list.addHandler(ctx);
		
		server.setHandler(list);
		
		try {
			
			server.start();
			server.join();
		}
		catch (Throwable e) {
			//TODO Exception handling
			e.printStackTrace();
		}
	}	

	public static void testDOM(){
		
		URL url = null;
		
		try {
//			url = new URL("http://www.cs.bgu.ac.il/~dwss121/Announcements?action=rss");
			url = new URL("http://plasmasturm.org/feeds/plasmasturm/");
			
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		Map<String, String[]> filters = new HashMap<String, String[]>();
		
//		ArrayList<String> filterCategoryValue = new ArrayList<String>();
//		filterCategoryValue.add("News");
//		
//		filters.put("category", filterCategoryValue);
//		
//		ArrayList<String> filterAuthorValue = new ArrayList<String>();
//		filterAuthorValue.add("Guy Wiener");
//		
//		filters.put("author", filterAuthorValue);
//		
		String[] filterAuthorValue = new String[1];
		filterAuthorValue[0] = "Elegy to my only love in the cloud";
//		
		filters.put("title", filterAuthorValue);
		
		// Create an executor:
        ExecutorService e = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 1; i++)
        	e.execute(new DOMStreamReader(url, nodes, filters));

        // this causes the executor not to accept any more
        //tasks, and to kill all of its threads when all the
        //submitted tasks are done.
        e.shutdown();
        
        try {
			while (!e.awaitTermination(3, TimeUnit.SECONDS)) continue;
		}
        catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        Transformer transformer = null;
        
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		}
		catch (TransformerConfigurationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

        for (Node node: nodes){
        	
        	Source s = new DOMSource(node);
    		Result r = new StreamResult(System.out);

    		try {
    			transformer.transform(s, r);
    		}
    		catch (TransformerException e3) {
    			// TODO Auto-generated catch block
    			e3.printStackTrace();
    		}
        }
	}
}   