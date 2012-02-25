package aggrService.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import aggrService.servlet.AggrServlet;

public class Main {

	private static final int AGGR_SERVER_PORT = 17172;

	public static void main(final String[] args) {

		final Server server = new Server(AGGR_SERVER_PORT);

		final ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);

		ctx.setContextPath("");
		ctx.addServlet(new ServletHolder(new AggrServlet()), "/aggr/*");
		// ctx.addServlet(new ServletHolder(new HtmlServlet()), "/aggr/*");

		final ResourceHandler rh = new ResourceHandler();

		rh.setResourceBase("./html/");

		// TODO change back
		// rh.setWelcomeFiles(new String[] { "index.html" });
		// rh.setWelcomeFiles(new String[] { "tests.html" });
		rh.setWelcomeFiles(new String[] { "new2.html" });

		final HandlerList list = new HandlerList();

		list.addHandler(ctx);
		list.addHandler(rh);

		server.setHandler(list);

		while (true) {

			try {

				server.start();
				server.join();
				break;
			} catch (final Throwable e) {
				e.printStackTrace();
			}
		}
	}
}
