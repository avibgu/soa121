package dbService.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DBController;
import common.Post;


public class dbServlet extends HttpServlet {

	private static final long serialVersionUID = 6461646473709706660L;

	protected DBController mDBController;

	public dbServlet() throws Exception {

		this.mDBController = new DBController();
	}

	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {

		final Post post = new Post(req);

		this.mDBController.createNewPost(post);
		
		sendTextResponse(resp, "POSTED");
	}
	
	protected void sendTextResponse(HttpServletResponse resp,
			String answer) throws IOException {

		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		out.println(answer);
		out.close();
	}
}
