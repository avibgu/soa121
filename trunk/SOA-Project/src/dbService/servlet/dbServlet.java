package dbService.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;

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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}

	// <post>
	// 	<title></title>
	// 	<author></author>
	// 	<tags>
	// 		<tag></tag>
	// 		<tag></tag>
	// 	</tags>
	// 	<content></content>
	// </post>
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Post post = parseXMLToPost(req);

		mDBController.createNewPost(post);
	}

	private Post parseXMLToPost(HttpServletRequest req) {

		ArrayList<String> tagsList = new ArrayList<String>();

		Post post = new Post("title", new Date(new java.util.Date().getTime()),
				"content", "author", tagsList);
		return post;
	}
}
