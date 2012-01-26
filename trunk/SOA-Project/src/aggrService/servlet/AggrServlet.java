package aggrService.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.DBController;
import common.Post;

public class AggrServlet extends HttpServlet{

	private static final long serialVersionUID = -8677500568357771288L;

	protected DBController mDBController;

	public AggrServlet(){

		this.mDBController = new DBController();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		final long startDate = 0;
		final long endDate = 0;

		//TODO: if the user wants to get the BASIC HTML we will give him that..
		
		try {
			final ArrayList<Post> postsOfSpecificUser = this.mDBController
					.getPostsOfSpecificUser("username");
			final ArrayList<Post> postsBetweenSpecificDates = this.mDBController.getPostsBetweenSpecificDates(
					new Date(startDate), new Date(endDate));
			final ArrayList<Post> postsOfSomeTags = this.mDBController
					.getPostsOfTheseTags(new ArrayList<String>());
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
