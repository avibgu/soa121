package dbService.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Post;

import dbService.db.DBController;

public class dbServlet extends HttpServlet {

	private static final long serialVersionUID = 6461646473709706660L;

	protected DBController mDBController;

	public dbServlet() {

		mDBController = new DBController();
	}

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) {

		final long startDate = 0;
		final long endDate = 0;

		try {
			final ArrayList<Post> postsOfSpecificUser = mDBController.getPostsOfSpecificUser("username");
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mDBController.getPostsBetweenSpecificDates(new Date(startDate), new Date(endDate));
		mDBController.getPostsOfTheseTags(new ArrayList<String>());
	}

	// <post>
	// <title></title>
	// <author></author>
	// <tags>
	// <tag></tag>
	// <tag></tag>
	// <tag></tag>
	// <tag></tag>
	// <tag></tag>
	// </tags>
	// <content></content>
	// </post>
	@Override
	protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {

		final Post post = new Post(req);

		mDBController.createNewPost(post);
	}
}
