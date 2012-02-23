package dbService.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.util.ajax.JSON;

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
		final Post post = new Post(req);

		System.out.println("The post:");
		System.out.println(post);
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
