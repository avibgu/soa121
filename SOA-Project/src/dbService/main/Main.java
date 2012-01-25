package dbService.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import dbService.servlet.dbServlet;

public class Main {

	private static final int SERVER_PORT = 17171;

	public static void main(final String[] args) throws Exception {

		final Server server = new Server(SERVER_PORT);

		final ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.SESSIONS);

		ctx.setContextPath("");
		ctx.addServlet(new ServletHolder(new dbServlet()), "/*");

		final HandlerList list = new HandlerList();

		list.addHandler(ctx);

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
