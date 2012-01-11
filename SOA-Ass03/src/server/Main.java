package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.*;

import feeds.FeedHandler;

public class Main {
	
	public static final int		SERVER_PORT			= 8994;
	public static final String	SERVER_NAME			= "soa2";
	public static final String	FULL_SERVER_NAME	= "http://soa2.cs.bgu.ac.il:8994/ex3";

	public static void main(String[] args) {

		Server server = new Server(SERVER_PORT);

		ServletContextHandler ctx = 
			new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		ctx.setContextPath("/ex1");
		ctx.addServlet(new ServletHolder(new MainServlet(new FeedHandler())), "/*");
		
		
		ResourceHandler res = new ResourceHandler();
		
		res.setResourceBase("/resources");

		HandlerList list = new HandlerList();
		
		list.addHandler(ctx);
		list.addHandler(res);
		
		server.setHandler(list);
		
		while (true){
			
			try {
				
				server.start();
				server.join();
				break;
			}
			catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
}   