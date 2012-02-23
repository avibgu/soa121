package dbService.servlet;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DBController;
import common.Post;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class dbServlet extends HttpServlet {

	private static final long serialVersionUID = 6461646473709706660L;

	protected DBController mDBController;

	public dbServlet() throws Exception {
		this.mDBController = null;
		// this.mDBController = new DBController();
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {

		System.out.println("posting " + req.getRequestURL());
		String postJson = "";
		try {
			postJson = new java.util.Scanner(req.getInputStream()).useDelimiter("\\A").next();
		} catch (final java.util.NoSuchElementException e) {
		}

		System.out.println(postJson);
		// final JSONObject postJsonObject = new JSONObject(postJson);
		//
		// final Post post = new Post(req);
		// System.out.println("The post:");
		// System.out.println(post);
		// this.mDBController.createNewPost(post);
		this.sendTextResponse(resp, "POSTED");
	}

	protected void sendTextResponse(final HttpServletResponse resp, final String answer) throws IOException {

		resp.setCharacterEncoding("UTF-8");
		final PrintWriter out = resp.getWriter();
		out.println(answer);
		out.close();
	}
}
