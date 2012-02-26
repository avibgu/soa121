package common;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Post {

	private String mTitle;
	private Timestamp mDate;
	private String mContent;
	private String mAuthor;
	private ArrayList<String> mTags;

	public Post(final String pTitle, final Timestamp pDate, final String pContent, final String pAuthor,
			final ArrayList<String> pTags) {

		this.mTitle = pTitle;
		this.mDate = pDate;
		this.mContent = pContent;
		this.mAuthor = pAuthor;
		this.mTags = pTags;
	}

	public Post(final HttpServletRequest req) {

		try {
			this.parseJSONToPost(req);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// <title></title>
	// <author></author>
	// <tags>
	// <tag1></tag>
	// <tag2></tag>
	// <tag3></tag>
	// ...
	// </tags>
	// <content></content>
	private void parseJSONToPost(final HttpServletRequest req) throws Exception {

		this.mTags = new ArrayList<String>();
		this.mDate = new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());

		// read the input
		final BufferedInputStream bis = new BufferedInputStream(req.getInputStream());
		final ByteArrayOutputStream buf = new ByteArrayOutputStream();
		int result = bis.read();
		while (result != -1) {
			final byte b = (byte) result;
			buf.write(b);
			result = bis.read();
		}
		String postJsonString = buf.toString();

		// decode to json
		postJsonString = this.decodeURIComponent2(postJsonString);
		// System.out.println("the decoded json = " + postJson);

		// before =
		// data=[{"title":"title1","author":"author1","tags":{"tag1":"tag11","tag2":"tag21","tag3":"tag31","tag4":"tag41","tag5":"tag51"}}]
		postJsonString = postJsonString.substring(postJsonString.indexOf("[") + 1,
				postJsonString.lastIndexOf("]"));
		// System.out.println("the decoded json 2 = " + postJson);

		// convert the string to json
		// after =
		// {"title":"title1","author":"author1","tags":{"tag1":"tag11","tag2":"tag21","tag3":"tag31","tag4":"tag41","tag5":"tag51"}}
		final JSONObject postJson = JSONObject.fromObject(postJsonString);
		// System.out.println("title = " + postJson.get("title"));

		// get the titles
		try {
			this.mTitle = postJson.getString("title");
		} catch (final Exception e) {
			this.mTitle = "";
		}
		try {
			this.mAuthor = postJson.getString("author");
		} catch (final Exception e) {
			this.mAuthor = "";
		}
		try {
			this.mContent = postJson.getString("content");
		} catch (final Exception e) {
			this.mContent = "";
		}
		try {
			final JSONObject tags = postJson.getJSONObject("tags");
			final JSONArray tagNames = tags.names();
			if (tagNames != null) {
				for (int j = 0; j < tagNames.size(); j++) {
					this.mTags.add(postJson.getString(tagNames.getString(j)));
				}
			}
		} catch (final Exception e) {
		}

	}

	//
	//
	// private void parseJSONToPost(final HttpServletRequest req) throws
	// Exception {
	//
	// this.mTags = new ArrayList<String>();
	// this.mDate = new
	// java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
	//
	// final TransformerFactory f = TransformerFactory.newInstance();
	// final Transformer t = f.newTransformer();
	//
	// final Source s = new StreamSource(req.getInputStream());
	// final Result r = new DOMResult();
	//
	// t.transform(s, r);
	//
	// final Node n = ((DOMResult) r).getNode();
	//
	// final NodeList childs = n.getChildNodes();
	//
	// for (int i = 0; i < childs.getLength(); i++) {
	//
	// final Node child = childs.item(i);
	//
	// if (child.getNodeName().equals("title")) {
	// this.mTitle = child.getNodeValue();
	// } else if (child.getNodeName().equals("author")) {
	// this.mAuthor = child.getNodeValue();
	// } else if (child.getNodeName().equals("content")) {
	// this.mContent = child.getNodeValue();
	// } else if (child.getNodeName().equals("tags")) {
	//
	// final NodeList tags = child.getChildNodes();
	//
	// for (int j = 0; j < tags.getLength(); j++) {
	// this.mTags.add(tags.item(j).getNodeValue());
	// }
	// }
	// }
	// }

	// <title></title>
	// <author></author>
	// <date></date>
	// <tags>
	// <tag></tag>
	// <tag></tag>
	// <tag></tag>
	// <tag></tag>
	// <tag></tag>
	// </tags>
	// <content></content>
	public String toJSON() {

		// DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);

		final StringBuilder sb = new StringBuilder();

		sb.append("{");
		sb.append("\"title\":\"" + this.mTitle + "\", ");
		sb.append("\"author\":\"" + this.mAuthor + "\", ");
		sb.append("\"date\":\"" + this.mDate + "\", ");
		
		if (!this.mTags.isEmpty()){
			
			sb.append("\"tags\":{");
			
			int i = 0;
			for (final String tag : this.mTags) {
				sb.append("\"tag\"" + i + ":\"" + tag + "\", ");
				i++;
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.deleteCharAt(sb.length() - 1);
			sb.append("}, ");
		}


		sb.append("\"content\":\"" + this.mContent);
		sb.append("\"}");

		return sb.toString();
	}

	/**
	 * Decodes the passed UTF-8 String using an algorithm that's compatible with
	 * JavaScript's <code>decodeURIComponent</code> function. Returns
	 * <code>null</code> if the String is <code>null</code>.
	 * 
	 * @param s
	 *            The UTF-8 encoded String to be decoded
	 * @return the decoded String
	 */
	private String decodeURIComponent2(final String encodedURI) {
		char actualChar;

		final StringBuffer buffer = new StringBuffer();

		int bytePattern, sumb = 0;

		for (int i = 0, more = -1; i < encodedURI.length(); i++) {
			actualChar = encodedURI.charAt(i);

			switch (actualChar) {
			case '%': {
				actualChar = encodedURI.charAt(++i);
				final int hb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character
						.toLowerCase(actualChar) - 'a') & 0xF;
				actualChar = encodedURI.charAt(++i);
				final int lb = (Character.isDigit(actualChar) ? actualChar - '0' : 10 + Character
						.toLowerCase(actualChar) - 'a') & 0xF;
				bytePattern = hb << 4 | lb;
				break;
			}
			case '+': {
				bytePattern = ' ';
				break;
			}
			default: {
				bytePattern = actualChar;
			}
			}

			if ((bytePattern & 0xc0) == 0x80) { // 10xxxxxx
				sumb = sumb << 6 | bytePattern & 0x3f;
				if (--more == 0) {
					buffer.append((char) sumb);
				}
			} else if ((bytePattern & 0x80) == 0x00) { // 0xxxxxxx
				buffer.append((char) bytePattern);
			} else if ((bytePattern & 0xe0) == 0xc0) { // 110xxxxx
				sumb = bytePattern & 0x1f;
				more = 1;
			} else if ((bytePattern & 0xf0) == 0xe0) { // 1110xxxx
				sumb = bytePattern & 0x0f;
				more = 2;
			} else if ((bytePattern & 0xf8) == 0xf0) { // 11110xxx
				sumb = bytePattern & 0x07;
				more = 3;
			} else if ((bytePattern & 0xfc) == 0xf8) { // 111110xx
				sumb = bytePattern & 0x03;
				more = 4;
			} else { // 1111110x
				sumb = bytePattern & 0x01;
				more = 5;
			}
		}
		return buffer.toString();
	}

	@Override
	public String toString() {
		return this.toJSON();
	}

	public String getTitle() {
		return this.mTitle;
	}

	public void setTitle(final String mTitle) {
		this.mTitle = mTitle;
	}

	public Timestamp getDate() {
		return this.mDate;
	}

	public void setDate(final Timestamp mDate) {
		this.mDate = mDate;
	}

	public String getContent() {
		return this.mContent;
	}

	public void setContent(final String mContent) {
		this.mContent = mContent;
	}

	public String getAuthor() {
		return this.mAuthor;
	}

	public void setAuthor(final String mAuthor) {
		this.mAuthor = mAuthor;
	}

	public ArrayList<String> getTags() {
		return this.mTags;
	}

	public void setTags(final ArrayList<String> mTags) {
		this.mTags = mTags;
	}

}
