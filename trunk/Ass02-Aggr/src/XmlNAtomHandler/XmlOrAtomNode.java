package XmlNAtomHandler;

import java.util.Hashtable;

/**
 * Utility class for RSS/ATOM conversion
 * 
 * @author niram
 * 
 */
public class XmlOrAtomNode {

	// key = "session" id, value = result node
	private static Hashtable<Integer, StringBuffer> instances = new Hashtable<Integer, StringBuffer>();

	/**
	 * implements singleton for each GET request
	 * 
	 * @param session
	 * @return
	 */
	public static synchronized StringBuffer getXmlInstance(int session) {
		StringBuffer res = instances.get(new Integer(session));
		if (res == null) {
			res = new StringBuffer();
			instances.put(new Integer(session), res);
		}
		return res;
	}

	/**
	 * RSS prefix
	 * 
	 * @return
	 */
	public static String getPrefix() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
				+ "<rss version=\"2.0\">\n" + "<channel>\n";
	}

	/**
	 * RSS suffix
	 * 
	 * @return
	 */
	public static String getSuffix() {
		return "</channel>\n" + "</rss>";
	}

	/**
	 * convert ATOM to RSS
	 * 
	 * @param allItems
	 * @return
	 */
	public static String atomToXml(String allItems) {
		allItems = allItems.replaceAll("<entry>", "<item>");
		allItems = allItems.replaceAll("<subtitle>", "<description>");
		allItems = allItems.replaceAll("<content>", "<description>");
		allItems = allItems.replaceAll("<summary>", "<description>");

		allItems = allItems.replaceAll("</entry>", "</item>");
		allItems = allItems.replaceAll("</subtitle>", "</description>");
		allItems = allItems.replaceAll("</content>", "</description>");
		allItems = allItems.replaceAll("</summary>", "</description>");

		return allItems;
	}

}
