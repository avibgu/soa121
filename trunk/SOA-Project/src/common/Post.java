package common;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Post {

	private String mTitle;
	private Date mDate;
	private String mContent;
	private String mAuthor;
	private ArrayList<String> mTags;

	public Post(final String pTitle, final Date pDate, final String pContent, final String pAuthor,
			final ArrayList<String> pTags) {

		this.mTitle = pTitle;
		this.mDate = pDate;
		this.mContent = pContent;
		this.mAuthor = pAuthor;
		this.mTags = pTags;
	}

	public Post(final HttpServletRequest req) {

		try {
			parseXMLToPost(req);
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
	private void parseXMLToPost(final HttpServletRequest req) throws Exception {

		mTags = new ArrayList<String>();
		mDate = new Date(new java.util.Date().getTime());

		final TransformerFactory f = TransformerFactory.newInstance();
		final Transformer t = f.newTransformer();

		final Source s = new StreamSource(req.getInputStream());
		final Result r = new DOMResult();

		t.transform(s, r);

		final Node n = ((DOMResult) r).getNode();

		final NodeList childs = n.getChildNodes();

		for (int i = 0; i < childs.getLength(); i++) {

			final Node child = childs.item(i);

			if (child.getNodeName().equals("title"))
				mTitle = child.getNodeValue();
			
			else if (child.getNodeName().equals("author"))
				mAuthor = child.getNodeValue();
			
			else if (child.getNodeName().equals("content"))
				mContent = child.getNodeValue();
			
			else if (child.getNodeName().equals("tags")) {

				final NodeList tags = child.getChildNodes();

				for (int j = 0; j < tags.getLength(); j++)
					mTags.add(tags.item(j).getNodeValue());
			}
		}
	}

	// <post>
	// <title></title>
	// <author></author>
	// <date></date>		TODO
	// <tags>
	// <tag></tag>
	// <tag></tag>			TODO: why just 5?...
	// <tag></tag>
	// <tag></tag>
	// <tag></tag>
	// </tags>
	// <content></content>
	// </post>
	public String toXML() {
		
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<post>\n");
		sb.append("	<title>" + mTitle +"</title>\n");
		sb.append("	<author>" + mAuthor +"</author>\n");
		sb.append("	<date>" + df.format(mDate) +"</date>\n");
		sb.append("	<tags>\n");
		
		for (String tag : mTags)
			sb.append("		<tag>" + tag +"</tag>\n");
		
		sb.append("	</tags>\n");
		sb.append("	<content>" + mContent +"</content>\n");
		sb.append("<post>\n");

		return sb.toString();
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(final String mTitle) {
		this.mTitle = mTitle;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(final Date mDate) {
		this.mDate = mDate;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(final String mContent) {
		this.mContent = mContent;
	}

	public String getAuthor() {
		return mAuthor;
	}

	public void setAuthor(final String mAuthor) {
		this.mAuthor = mAuthor;
	}

	public ArrayList<String> getTags() {
		return mTags;
	}

	public void setTags(final ArrayList<String> mTags) {
		this.mTags = mTags;
	}

}
