package main;

import org.aggregate.news.NewsAggrStub;
import org.aggregate.news.NewsAggrStub.Channel;
import org.aggregate.news.NewsAggrStub.GetNewsReq;
import org.aggregate.news.NewsAggrStub.Item_type0;
import org.apache.axis2.AxisFault;
import org.subscription.news.NewsSubsStub;
import org.subscription.news.NewsSubsStub.PostCollectionRequest;
import org.subscription.news.NewsSubsStub.PostCollectionResponse;
import org.subscription.news.NewsSubsStub.PutElementRequest;
import org.subscription.news.NewsSubsStub.PutElementResponse;

public class Main {

	public static void main(String[] args) {

		System.out.println("main started");
		System.out.flush();

		try {
//			putGet();
			postGet();
//			putGetDeleteGet();
//			postGetDeleteGet();
//			putGetWithFilters();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void putGet() throws Exception {

		NewsSubsStub subs = new NewsSubsStub();

		PutElementRequest per = new PutElementRequest();

		per.setName("/test2");
		per.setUrl("http://www.cs.bgu.ac.il/~gwiener/feed3.xml");

		PutElementResponse response = subs.putElement(per);

		System.out.println("response: " + response.getPutElementResponse());

		NewsAggrStub aggr = new NewsAggrStub();
		GetNewsReq gnr = new GetNewsReq();
		gnr.setFeed("/test2");
		Channel ch = aggr.getNews(gnr);

		int i = 1;

		for (Item_type0 item : ch.getItem()) {

			System.out.println("ITEM " + i++ + ":");

			System.out.println(item.getTitle());
			System.out.println(item.getAuthor());
			System.out.println(item.getCategory());
			System.out.println(item.getDescription() + "\n");
		}
	}

	private static void postGet() throws Exception {

		NewsSubsStub subs = new NewsSubsStub();

		PostCollectionRequest pcr = new PostCollectionRequest();

		pcr.setName("/test3/");
		pcr.setUrl("http://dl.dropbox.com/u/28965452/feed4.xml");

		PostCollectionResponse response = subs.postCollection(pcr);

		System.out.println("response: " + response.getPostCollectionResponse());

		NewsAggrStub aggr = new NewsAggrStub();
		GetNewsReq gnr = new GetNewsReq();
		gnr.setFeed("/test3/");
		Channel ch = aggr.getNews(gnr);

		int i = 1;

		for (Item_type0 item : ch.getItem()) {

			System.out.println("ITEM " + i++ + ":");

			System.out.println(item.getTitle());
			System.out.println(item.getAuthor());
			System.out.println(item.getCategory());
			System.out.println(item.getDescription() + "\n");
		}
	}

	private static void putGetDeleteGet() {
		// TODO Auto-generated method stub
	}

	private static void postGetDeleteGet() {
		// TODO Auto-generated method stub
	}

	private static void putGetWithFilters() {
		// TODO Auto-generated method stub
	}
}
