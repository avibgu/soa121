package aggrService.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
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

		this.mDBController = new DBController();
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		ArrayList<Post> posts = null;
		
		Map<String,String[]> parameters = req.getParameterMap();
		
		try {
			
			if (parameters.containsKey("author")){
				
				String author = parameters.get("author")[0];
				posts = this.mDBController.getPostsOfSpecificAuthor(author);
			}
			else if (parameters.containsKey("startDate") && parameters.containsKey("endDate")){
				
				long startDate = Long.parseLong(parameters.get("startDate")[0]);
				long endDate = Long.parseLong(parameters.get("endDate")[0]);
				
				posts = this.mDBController.getPostsBetweenSpecificDates(new Date(
						startDate), new Date(endDate));
			}
			else if (parameters.containsKey("tag")){
				
				String[] tags = parameters.get("tag");	//TODO how to send multiple tags...
				posts = this.mDBController.getPostsOfTheseTags(tags);
			}
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

		if (null == posts) return;
		
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		
		out.println("<posts>");
		
		for (Post post : posts)
			out.print(post.toXML());
		
		out.println("</posts>");
		
		out.close();
	}
}
