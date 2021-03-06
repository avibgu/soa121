package test;

import org.aggregate.news.Channel;
import org.aggregate.news.GetNewsReq;
import org.aggregate.news.Item_type0;
import org.aggregate.news.NewsAggrSkeleton;

/**
 * Test class
 * 
 * @author niram
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NewsAggrSkeleton test = new NewsAggrSkeleton();
		GetNewsReq getNewsReq0 = new GetNewsReq();
		getNewsReq0.setFeed("http://www.little-lisper.org/feed1.xml");
		// System.out.println("before");
		// test.getNews(getNewsReq0);
		// System.out.println("after");
		System.out.println(toString(test.getNews(getNewsReq0)));

	}

	/**
	 * for Debug
	 * 
	 * @param i
	 * @return
	 */
	public static String toString(Item_type0 i) {
		String result = "";
		result += "\t<item>\n";
		if (i.getAuthor() != null) {
			result += "\t\t<author>" + i.getAuthor() + "</author>\n";
		}
		if (i.getTitle() != null) {
			result += "\t\t<title>" + i.getTitle() + "</title>\n";
		}
		if (i.getCategory() != null) {
			result += "\t\t<category>" + i.getCategory() + "</category>\n";
		}
		if (i.getDescription() != null) {
			result += "\t\t<description>" + i.getDescription()
					+ "</description>\n";
		}
		result += "\t</item>\n";
		return result;

	}

	/**
	 * for Debug
	 * 
	 * @param c
	 * @return
	 */
	public static String toString(Channel c) {
		String result = "";
		result += "<channel>\n";
		if (c.getItem() != null)
			for (int i = 0; i < c.getItem().length; i++) {
				result += toString(c.getItem()[i]);
			}

		result += "</channel>\n";
		return result;

	}
}
