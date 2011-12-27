/**
 * NewsAggrSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.1  Built on : Aug 31, 2011 (12:22:40 CEST)
 */
package org.aggregate.news;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import XmlNAtomHandler.XmlHandler;
import XmlNAtomHandler.XmlOrAtomNode;

/**
 * NewsAggrSkeleton java skeleton for the axisService
 */
public class NewsAggrSkeleton implements NewsAggrSkeletonInterface {

	private static int sessionCounter = 0;

	/**
	 * Auto generated method signature
	 * 
	 * @param getNewsReq0
	 * @return channel1
	 */

	public org.aggregate.news.Channel getNews(GetNewsReq getNewsReq0) {

		// business logic

		// throw new java.lang.UnsupportedOperationException("Please implement "
		// + this.getClass().getName() + "#getNews");

		// NewsAggrStub na = new NewsAggrStub("http://soa2:17171/ex2");
		//
		// GetNewsReq nr = new GetNewsReq();
		//
		// nr.setFeed("http://news.feedzilla.com/en_us/headlines/sports/football-nfl.rss");
		//
		// NewsAggrStub.Channel s = na.getNews(nr);

		// TODO: get the url from the sub service
		// TODO: check it with the xmls from ass1 grading
		Vector<String> urls = new Vector<String>();
		// urls.add("http://www.cs.bgu.ac.il/~dwss121/Forum?action=rss");
		// urls.add("http://www.little-lisper.org/feed1.xml");
		// urls.add("http://www.cs.bgu.ac.il/~gwiener/feed3.xml");
		urls.add("/users/studs/bsc/2010/niram/workspace/Ass02-Aggr/test.xml");
		// urls.add("http://www.cs.bgu.ac.il/~dwss121/Announcements?action=rss");
		// urls
		// .add("http://www.cs.bgu.ac.il/~dwss121/CsWiki/RecentChanges?action=rss");

		Hashtable<String, Vector<String>> filters = getFilters(getNewsReq0);

		return handleMultiUrls(filters, urls);

	}

	/**
	 * TODO: fill this function
	 * 
	 * @param getNewsReq0
	 * @return the filters from the request
	 */
	private Hashtable<String, Vector<String>> getFilters(GetNewsReq getNewsReq0) {
		// TODO Auto-generated method stub
		Hashtable<String, Vector<String>> filters = new Hashtable<String, Vector<String>>();
		Vector<String> author = new Vector<String>();
		author.add("mike");
		filters.put("author", author);

		Vector<String> title = new Vector<String>();
		title.add("t1");
		filters.put("title", title);

		return filters;
	}

	/**
	 * fetch and handle multiple urls.
	 * 
	 * @param response
	 * @param filters
	 * @param col
	 * @throws IOException
	 */
	private org.aggregate.news.Channel handleMultiUrls(
			Hashtable<String, Vector<String>> filters, Vector<String> urls) {
		Vector<Thread> thr = new Vector<Thread>();
		for (int i = 0; i < urls.size(); i++) {
			Thread t = new Thread(new XmlHandler(urls.get(i), sessionCounter,
					filters));
			thr.add(t);
			try {
				t.start();
			} catch (Exception e) {
				// handle bad request
				return handleBadRequest();
			}
		}

		for (int i = 0; i < urls.size(); i++) {
			try {
				thr.get(i).join();
			} catch (InterruptedException e) {
				// handle bad request
				return handleBadRequest();
			}
		}
		String allItems = XmlOrAtomNode.getXmlInstance(sessionCounter)
				.toString();
		// allItems = XmlOrAtomNode.getPrefix() + allItems
		// + XmlOrAtomNode.getSuffix();
		allItems = XmlOrAtomNode.atomToXml(allItems);

		org.aggregate.news.Channel resultChannel = convertItemsToChannel(allItems);
		sessionCounter++;
		return resultChannel;
	}

	/**
	 * return empty channel, indicating bad request
	 * 
	 * @return Bad channel
	 */
	private Channel handleBadRequest() {
		// TODO Auto-generated method stub
		Channel badChannel = new Channel();

		return badChannel;
	}

	/**
	 * converts string of channel to channel object
	 * 
	 * @param allItems
	 * @return
	 */
	private Channel convertItemsToChannel(String allItems) {
		Channel ch = new Channel();
		// create org.aggregate.news.Item_type0[] param
		// use ch.setItem(param)
		// go over the items and do addItem

		int itemIndexStart = allItems.indexOf("<item>");
		int itemIndexEnd = allItems.indexOf("</item>") + "</item>".length();
		while ((itemIndexStart != -1)
				&& (itemIndexEnd != -1 + "</item>".length())) {
			String item = allItems.substring(itemIndexStart, itemIndexEnd);

			org.aggregate.news.Item_type0 itemType0 = createItem(item);
			ch.addItem(itemType0);

			allItems = allItems.substring(itemIndexEnd + 1);
			itemIndexStart = allItems.indexOf("<item>");
			itemIndexEnd = allItems.indexOf("</item>") + "</item>".length();
		}

		return ch;
	}

	/**
	 * converts string of item to Item_type0 object
	 * 
	 * @param item
	 *            - item as string
	 * @return the item as Item_type0
	 */
	private Item_type0 createItem(String item) {
		Item_type0 result = new Item_type0();

		// gets the - author title category description, to the result
		// for each part, get the string between the <type> .. </type> - the
		// type content
		// and update the result with it

		int authorIndexStart = item.indexOf("<author>");
		int authorIndexEnd = item.indexOf("</author>");
		if (authorIndexStart != -1) {
			authorIndexStart += "<author>".length();
			// System.out.println("the author = ");
			// System.out.println("--"
			// + item.substring(authorIndexStart, authorIndexEnd) + "--");
			result.setAuthor(item.substring(authorIndexStart, authorIndexEnd));
		}

		int titleIndexStart = item.indexOf("<title>");
		int titleIndexEnd = item.indexOf("</title>");
		if (titleIndexStart != -1) {
			titleIndexStart += "<title>".length();
			// System.out.println("the title = ");
			// System.out.println("--"
			// + item.substring(titleIndexStart, titleIndexEnd) + "--");
			result.setTitle(item.substring(titleIndexStart, titleIndexEnd));
		}

		int categoryIndexStart = item.indexOf("<category>");
		int categoryIndexEnd = item.indexOf("</category>");
		if (categoryIndexStart != -1) {
			categoryIndexStart += "<category>".length();
			// System.out.println("the category = ");
			// System.out.println("--"
			// + item.substring(categoryIndexStart, categoryIndexEnd)
			// + "--");
			result.setAuthor(item.substring(categoryIndexStart,
					categoryIndexEnd));
		}

		int descriptionIndexStart = item.indexOf("<description>");
		int descriptionIndexEnd = item.indexOf("</description>");
		if (descriptionIndexStart != -1) {
			descriptionIndexStart += "<description>".length();
			// System.out.println("the description = ");
			// System.out.println("--"
			// + item
			// .substring(descriptionIndexStart,
			// descriptionIndexEnd) + "--");
			result.setAuthor(item.substring(descriptionIndexStart,
					descriptionIndexEnd));
		}

		// System.out.println("----------------------");

		return result;
	}

}
