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
			final ArrayList<Post> postsOfSpecificUser1 = mDBController.getPostsOfSpecificUser("username");
			final ArrayList<Post> postsOfSpecificUser2 = mDBController.getPostsBetweenSpecificDates(new Date(
					startDate), new Date(endDate));
			final ArrayList<Post> postsOfSpecificUser3 = mDBController
					.getPostsOfTheseTags(new ArrayList<String>());
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
