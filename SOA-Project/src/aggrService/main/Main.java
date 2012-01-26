package aggrService.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import aggrService.servlet.*;

public class Main {

	private static final int SERVER_PORT = 17172;

	public static void main(String[] args) {

		Server server = new Server(SERVER_PORT);

		ServletContextHandler ctx =
			new ServletContextHandler(ServletContextHandler.SESSIONS);

		ctx.setContextPath("");
		ctx.addServlet(new ServletHolder(new AggrServlet()), "/aggr/*");

		ResourceHandler rh = new ResourceHandler();
		
		rh.setResourceBase("./html/");
		rh.setWelcomeFiles(new String[]{"index.html"});
		
		HandlerList list = new HandlerList();

		list.addHandler(ctx);
		list.addHandler(rh);

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
