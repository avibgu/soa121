package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import feeds.FeedHandler;

public class Main {

	public static final int SERVER_PORT = 8994;
	// public static final String SERVER_NAME = "soa2";
	public static final String SERVER_NAME = "127.0.0.1";
	public static final String FULL_SERVER_NAME = "http://127.0.0.1:8994/ex3";

	public static void main(final String[] args) {

		final Server server = new Server(SERVER_PORT);

		final ServletContextHandler ctx = new ServletContextHandler(
				ServletContextHandler.SESSIONS);

		ctx.setContextPath("/ex3");
		ctx.addServlet(new ServletHolder(new MainServlet(new FeedHandler())),
				"/*");

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