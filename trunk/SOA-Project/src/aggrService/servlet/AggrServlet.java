package aggrService.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

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

		this.mDBController = new DBController();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		final long startDate = 0;							//TODO get it from req
		final long endDate = 0;								//TODO get it from req
		final String author = "";							//TODO get it from req
		ArrayList<String> tags = new ArrayList<String>();	//TODO get it from req
		
		ArrayList<Post> posts = null;

		try {
			
			// TODO: decide which posts the user has requested..
			
			posts = this.mDBController.getPostsOfSpecificAuthor(author);
			posts = this.mDBController.getPostsBetweenSpecificDates(new Date(
					startDate), new Date(endDate));
			posts = this.mDBController.getPostsOfTheseTags(tags);
		}
		catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sendListOfPostsAsResponse(resp, posts);
	}

	//	<posts>
	//		<post></post>
	//		<post></post>
	//		...
	//		<post></post>
	//	</posts>
	protected void sendListOfPostsAsResponse(HttpServletResponse resp,
			ArrayList<Post> posts) throws IOException {

		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		
		out.println("<posts>");
		
		for (Post post : posts)
			out.print(post.toXML());
		
		out.println("</posts>");
		
		out.close();
	}
}
