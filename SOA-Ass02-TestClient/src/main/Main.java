package main;

import org.aggregate.news.NewsAggrStub;
import org.aggregate.news.NewsAggrStub.Channel;
import org.aggregate.news.NewsAggrStub.GetNewsReq;
import org.aggregate.news.NewsAggrStub.Item_type0;
import org.subscription.news.NewsSubsStub;
import org.subscription.news.NewsSubsStub.PutElementRequest;
import org.subscription.news.NewsSubsStub.PutElementResponse;

public class Main {

	public static void main(String[] args) {

		System.out.println("main started");

		System.out.flush();

		try {

			NewsSubsStub subs = new NewsSubsStub();

			PutElementRequest per = new PutElementRequest();

			per.setName("/test2");
			per.setUrl("http://www.cs.bgu.ac.il/~gwiener/feed3.xml");

			PutElementResponse response = subs.putElement(per);

//			System.out.println("response: " + response.getPutElementResponse());
//			GetURLsRequest gur = new GetURLsRequest();
//			gur.setIdentifier("/test2");
//			String[] uRLsList = subs.getURLs(gur).getURL();
//			for (int i=0;i<uRLsList.length;i++)
//			  System.out.println("uRLsList["+i+"]: " + uRLsList[i]);

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
//
//			public URLsList getURLs(GetURLsRequest getURLsRequest)
//			System.out.println("getURLs: " + response.getPutElementResponse());
//
//			NewsAggrStub aggr = new NewsAggrStub();
//
//			GetNewsReq gnr = new GetNewsReq();
//
//			gnr.setFeed("/test2");
//
//			Channel ch = aggr.getNews(gnr);
//
//			int i = 1;
//
//			for (Item_type0 item : ch.getItem()) {
//
//				System.out.println("ITEM " + i++ + ":");
//
//				System.out.println(item.getTitle());
//				System.out.println(item.getAuthor());
//				System.out.println(item.getCategory());
//				System.out.println(item.getDescription() + "\n");
//			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
