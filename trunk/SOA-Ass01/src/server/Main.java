package server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.*;

import xml.DOMStreamReader;
import xml.SAXStreamReader;

import feeds.FeedFactory;

public class Main {
	
	public static final int SERVER_PORT = 17171;

	public static void main(String[] args) {
			
		startServer();
		
		//	testSAX();
		//	testDOM();
	}
		
	public static void startServer(){
		
		Server server = new Server(SERVER_PORT);

		ServletContextHandler ctx = 
			new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		ctx.setContextPath("/ex1");
		ctx.addServlet(new ServletHolder(new MainServlet(FeedFactory.create(null))), "/");
		
//		// Files
//		ResourceHandler res = new ResourceHandler();
//		res.setResourceBase("files");
		
		HandlerList list = new HandlerList();
		
		list.addHandler(ctx);
//		list.addHandler(res);
		
		server.setHandler(list);
		
		try {
			
			server.start();
			server.join();
		}
		catch (Throwable e) {
			
			e.printStackTrace();
		}
	}
	
	public static void testSAX(){
		
		try {
			System.out.println(
				new SAXStreamReader().readFromStream(
					new URL("http://www.cs.bgu.ac.il/~dwss121/Announcements?action=rss")));
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testDOM(){
		
		URL url = null;
		
		try {
			url = new URL("http://www.cs.bgu.ac.il/~dwss121/Announcements?action=rss");
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		// Create an executor:
        ExecutorService e = Executors.newFixedThreadPool(7);

        for (int i=0;  i < 10;   i++)
        	e.execute(new DOMStreamReader(url, System.out));

        // this causes the executor not to accept any more
        //tasks, and to kill all of its threads when all the
        //submitted tasks are done.
        e.shutdown();
	}
}   