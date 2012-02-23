package aggrService.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DBController;
import common.Post;

public class AggrServlet extends HttpServlet {

	private static final long serialVersionUID = -8677500568357771288L;

	protected DBController mDBController;

	public AggrServlet() {
		this.mDBController = null;
		// this.mDBController = new DBController();
	}

	/**
	 * returns the main web page
	 * 
	 * @param resp
	 * @param file
	 * @throws IOException
	 */
	protected void sendFileResponse(final HttpServletResponse resp, final File file) throws IOException {
		// System.out.println("got request");
		final PrintWriter printWriter = resp.getWriter();

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {

			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			while (br.ready()) {
				printWriter.println(br.readLine());
			}

			printWriter.flush();

			fis.close();
			isr.close();
			br.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("getting " + req.getRequestURL());

		if (req.getRequestURI().equals("/aggr/scripts.js")) {
			// System.out.println("scripts.js request");
			return;
		}
		if (req.getRequestURI().equals("/aggr/")) {
			this.sendFileResponse(resp, new File("html/tests.html"));
			// System.out.println("get main page");
			return;
		}
		if (req.getRequestURI().equals("/aggr/jquery-1.7.1.js")) {
			return;
		}

		System.out.println("get 2");
		ArrayList<Post> posts = null;

		final Map<String, String[]> parameters = req.getParameterMap();

		try {

			if (parameters.containsKey("author")) {
				System.out.println("filtering by author");
				final String author = parameters.get("author")[0];
				posts = this.mDBController.getPostsOfSpecificAuthor(author);
			} else if (parameters.containsKey("startDate") && parameters.containsKey("endDate")) {
				System.out.println("filtering by dates");
				final long startDate = Long.parseLong(parameters.get("startDate")[0]);
				final long endDate = Long.parseLong(parameters.get("endDate")[0]);

				posts = this.mDBController.getPostsBetweenSpecificDates(new Timestamp(startDate),
						new Timestamp(endDate));
			} else if (parameters.containsKey("tag")) {
				System.out.println("filtering by tags");
				final String[] tags = parameters.get("tag"); // TODO how to send
				// multiple tags...

				posts = this.mDBController.getPostsOfTheseTags(tags);
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.sendListOfPostsAsResponse(resp, posts);
	}

	// <posts>
	// <post></post>
	// <post></post>
	// ...
	// <post></post>
	// </posts>
	protected void sendListOfPostsAsResponse(final HttpServletResponse resp, final ArrayList<Post> posts)
			throws IOException {

		if (posts == null) {
			return;
		}

		resp.setCharacterEncoding("UTF-8");
		final PrintWriter out = resp.getWriter();

		out.println("<posts>");

		for (final Post post : posts) {
			out.print(post.toXML());
		}

		out.println("</posts>");

		out.close();
	}
}
