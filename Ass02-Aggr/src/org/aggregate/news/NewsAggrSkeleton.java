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

		// TODO : fill this with the necessary business logic

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
		Vector<String> urls = new Vector<String>();
		urls.add("http://www.cs.bgu.ac.il/~dwss121/Forum?action=rss");
		urls.add("http://www.cs.bgu.ac.il/~dwss121/Announcements?action=rss");
		urls
				.add("http://www.cs.bgu.ac.il/~dwss121/CsWiki/RecentChanges?action=rss");

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
		return new Hashtable<String, Vector<String>>();
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
				// to do : handle bad request
				// handleBadRequest(response);
			}
		}

		for (int i = 0; i < urls.size(); i++) {
			try {
				thr.get(i).join();
			} catch (InterruptedException e) {
				// to do : handle bad request
				// handleBadRequest(response);
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
	 * TODO: do it first converts string of channel to channel object
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
		while (itemIndexStart != -1 && itemIndexEnd != -1 + "</item>".length()) {
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
	 * TODO: check this function its not tested
	 * 
	 * @param item
	 *            - item as string
	 * @return the item as Item_type0
	 */
	private Item_type0 createItem(String item) {
		Item_type0 result = new Item_type0();
		// author title category description

		int authorIndexStart = item.indexOf("<author>");
		int authorIndexEnd = item.indexOf("</author>");
		if (authorIndexStart != -1) {
			authorIndexStart += "<author>".length();
			authorIndexEnd -= 1;
			result.setAuthor(item.substring(authorIndexStart, authorIndexEnd));
		}

		int titleIndexStart = item.indexOf("<title>");
		int titleIndexEnd = item.indexOf("</title>");
		if (titleIndexStart != -1) {
			titleIndexStart += "<title>".length();
			titleIndexEnd -= 1;
			result.setTitle(item.substring(titleIndexStart, titleIndexEnd));
		}

		int categoryIndexStart = item.indexOf("<category>");
		int categoryIndexEnd = item.indexOf("</category>");
		if (categoryIndexStart != -1) {
			categoryIndexStart += "<category>".length();
			categoryIndexEnd -= 1;
			result.setAuthor(item.substring(categoryIndexStart,
					categoryIndexEnd));
		}

		int descriptionIndexStart = item.indexOf("<description>");
		int descriptionIndexEnd = item.indexOf("</description>");
		if (descriptionIndexStart != -1) {
			descriptionIndexStart += "<description>".length();
			descriptionIndexEnd -= 1;
			result.setAuthor(item.substring(descriptionIndexStart,
					descriptionIndexEnd));
		}

		return result;
	}

}
