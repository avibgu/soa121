package dbService.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Post post = null;

		try {
			post = parseXMLToPost(req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mDBController.createNewPost(post);
	}

	private Post parseXMLToPost(HttpServletRequest req) throws Exception {

		TransformerFactory f = TransformerFactory.newInstance();
		Transformer t = f.newTransformer();

		Source s = new StreamSource(req.getInputStream());
		Result r = new DOMResult();

		t.transform(s, r);

		Node n = ((DOMResult) r).getNode();

		String title = "";
		String author = "";
		ArrayList<String> tagsList = new ArrayList<String>();
		String content = "";

		NodeList childs = n.getChildNodes();

		for (int i = 0; i < childs.getLength(); i++) {

			Node child = childs.item(i);

			if (child.getNodeName().equals("title"))
				title = child.getNodeValue();

			else if (child.getNodeName().equals("author"))
				author = child.getNodeValue();

			else if (child.getNodeName().equals("content"))
				content = child.getNodeValue();

			else if (child.getNodeName().equals("tags")) {

				NodeList tags = child.getChildNodes();

				for (int j = 0; j < tags.getLength(); j++)
					tagsList.add(tags.item(j).getNodeValue());
			}
		}

		Post post = new Post(title, new Date(new java.util.Date().getTime()),
				content, author, tagsList);

		return post;
	}
}
