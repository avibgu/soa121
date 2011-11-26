package server;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.*;

import xml.SAXStreamReader;

import feeds.FeedCollection;

public class Main {
	
	public static final int SERVER_PORT = 17171;

	public static void main(String[] args) {
			
		startServer();
	}
		
	public static void startServer(){
		
		Server server = new Server(SERVER_PORT);

		ServletContextHandler ctx = 
			new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		ctx.setContextPath("/ex1");
		ctx.addServlet(new ServletHolder(new MainServlet(new FeedCollection("ex1",null))), "/");
		
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
	
	public static void test(){
		
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
}   