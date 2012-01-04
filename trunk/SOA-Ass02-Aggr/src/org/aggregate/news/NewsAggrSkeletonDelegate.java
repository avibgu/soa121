package org.aggregate.news;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.axis2.AxisFault;
import org.subscription.news.NewsSubsStub;
import org.subscription.news.NewsSubsStub.GetURLsRequest;
import org.subscription.news.NewsSubsStub.URLsList;

import XmlNAtomHandler.XmlHandler;
import XmlNAtomHandler.XmlOrAtomNode;

public class NewsAggrSkeletonDelegate implements NewsAggrSkeletonInterface {

	private static int sessionCounter = 0;

	/**
	 * Auto generated method signature
	 * 
	 * @param getNewsReq0
	 * @return channel1
	 * @throws AxisFault
	 */
	public org.aggregate.news.Channel getNews(GetNewsReq getNewsReq0) {

		// business logic

		NewsSubsStub subscriptionService;
		
		try {
			
			subscriptionService = new NewsSubsStub();

			GetURLsRequest getURLsRequest = new GetURLsRequest();
			getURLsRequest.setIdentifier(getNewsReq0.getFeed());

			URLsList urlList = subscriptionService.getURLs(getURLsRequest);
			String[] urlsArray = urlList.getURL();

			// convert the array to vector
			Vector<String> urls = new Vector<String>();
			for (int i = 0; i < urlsArray.length; i++) {
				urls.add(urlsArray[i]);
			}

			// get the urls
			Hashtable<String, Vector<String>> filters = getFilters(getNewsReq0);

			// fetch the urls
			return handleMultiUrls(filters, urls);
		} catch (AxisFault e) {
			System.out.println("AxisFault - getNews");
//			e.printStackTrace();

			return handleBadRequest();
		} catch (RemoteException e) {
			System.out.println("RemoteException - getNews");
//			e.printStackTrace();
			return handleBadRequest();
		}

		// GetNewsReq nr = new GetNewsReq();
		//
		// nr
		// .setFeed("http://news.feedzilla.com/en_us/headlines/sports/football-nfl.rss");
		// Vector<String> urls = new Vector<String>();
		// urls.add("http://www.cs.bgu.ac.il/~dwss121sdfsfsd/Forum?action=rss");
		// // urls.add("http://www.little-lisper.org/feed1.xml");
		// // urls.add("http://www.cs.bgu.ac.il/~gwiener/feed3.xml");
		// //
		// urls.add("/users/studs/bsc/2010/niram/workspace/Ass02-Aggr/test.xml");
		// //
		// urls.add("http://www.cs.bgu.ac.il/~dwss121/Announcements?action=rss");
		// // urls
		// //
		// .add("http://www.cs.bgu.ac.il/~dwss121/CsWiki/RecentChanges?action=rss");
		//
		// Channel ch = handleMultiUrls(new Hashtable<String, Vector<String>>(),
		// urls);
		// return ch;
	}

	/**
	 * 
	 * @param getNewsReq0
	 * @return the filters from the request
	 */
	private Hashtable<String, Vector<String>> getFilters(GetNewsReq getNewsReq0) {
		Hashtable<String, Vector<String>> filters = new Hashtable<String, Vector<String>>();

		// add the author filter
		if (getNewsReq0.localAuthorTracker) {
			Vector<String> author = new Vector<String>();
			author.add(getNewsReq0.localAuthor);
			filters.put("author", author);
		}

		// add the category filter
		if (getNewsReq0.localCategoryTracker) {
			Vector<String> category = new Vector<String>();
			category.add(getNewsReq0.localCategory);
			filters.put("category", category);
		}

		// add the title filter
		if (getNewsReq0.localTitleTracker) {
			Vector<String> title = new Vector<String>();
			title.add(getNewsReq0.localTitle);
			filters.put("title", title);
		}
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
				System.out.println("Exception - handleMultiUrls - 1");
				return handleBadRequest();
			}
		}

		for (int i = 0; i < urls.size(); i++) {
			try {
				thr.get(i).join();
			} catch (InterruptedException e) {
				// handle bad request
				System.out.println("Exception - handleMultiUrls - 2");
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
	 * 
	 * @return Bad channel
	 */
	private Channel handleBadRequest() {
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

			// System.out.println(item);
			// System.out.println("--------------start");
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
			result.setCategory(item.substring(categoryIndexStart,
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
			result.setDescription(item.substring(descriptionIndexStart,
					descriptionIndexEnd));
		}
		// System.out.println("THE author = " + result.getAuthor());
		// System.out.println("THE title = " + result.getTitle());
		// System.out.println("THE category = " + result.getCategory());
		// System.out.println("THE description = " + result.getDescription());
		// System.out.println("----------------------");

		return result;
	}

}
