package aggrService.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import aggrService.servlet.AggrServlet;
import aggrService.servlet.HttpPageServlet;

public class Main {

	private static final int SERVER_PORT = 17172;

	public static void main(String[] args) {

		Server server = new Server(SERVER_PORT);

		ServletContextHandler ctx =
			new ServletContextHandler(ServletContextHandler.SESSIONS);

		ctx.setContextPath("");
		ctx.addServlet(new ServletHolder(new AggrServlet()), "/aggr/*");
		ctx.addServlet(new ServletHolder(new HttpPageServlet()), "/");		//TODO in case there is a problem to get index.html, change it to "/*"

		HandlerList list = new HandlerList();

		list.addHandler(ctx);

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
