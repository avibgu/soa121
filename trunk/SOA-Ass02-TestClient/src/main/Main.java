package main;

import org.aggregate.news.NewsAggrStub;
import org.aggregate.news.NewsAggrStub.Channel;
import org.aggregate.news.NewsAggrStub.GetNewsReq;
import org.aggregate.news.NewsAggrStub.Item_type0;
import org.subscription.news.NewsSubsStub;
import org.subscription.news.NewsSubsStub.DeleteCollectionRequest;
import org.subscription.news.NewsSubsStub.DeleteCollectionResponse;
import org.subscription.news.NewsSubsStub.DeleteElementRequest;
import org.subscription.news.NewsSubsStub.DeleteElementResponse;
import org.subscription.news.NewsSubsStub.PostCollectionRequest;
import org.subscription.news.NewsSubsStub.PostCollectionResponse;
import org.subscription.news.NewsSubsStub.PutElementRequest;
import org.subscription.news.NewsSubsStub.PutElementResponse;

public class Main {

	public static void main(String[] args) {

		System.out.println("main started");
		System.out.flush();

		try {

			NewsSubsStub subs = new NewsSubsStub();
			NewsAggrStub aggr = new NewsAggrStub();

			putGet(subs, aggr);
			postGet(subs, aggr);
			putGetDeleteGet(subs, aggr);
			postGetDeleteGet(subs, aggr);
			putGetWithFilters(subs, aggr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printChannel(Channel ch) {

		int i = 1;

		for (Item_type0 item : ch.getItem()) {

			System.out.println("ITEM " + i++ + ":");

			System.out.println(item.getTitle());
			System.out.println(item.getAuthor());
			System.out.println(item.getCategory());
			System.out.println(item.getDescription() + "\n");
		}
	}

	private static void putGet(NewsSubsStub subs, NewsAggrStub aggr) throws Exception {

		PutElementRequest per = new PutElementRequest();

		per.setName("/test2");
		per.setUrl("http://www.cs.bgu.ac.il/~gwiener/feed3.xml");

		PutElementResponse response = subs.putElement(per);

		System.out.println("response: " + response.getPutElementResponse());

		GetNewsReq gnr = new GetNewsReq();
		gnr.setFeed("/test2");
		Channel ch = aggr.getNews(gnr);

		printChannel(ch);
	}

	private static void postGet(NewsSubsStub subs, NewsAggrStub aggr) throws Exception {

		PostCollectionRequest pcr = new PostCollectionRequest();

		pcr.setName("/test3/");
		pcr.setUrl("http://dl.dropbox.com/u/28965452/feed4.xml");

		PostCollectionResponse response = subs.postCollection(pcr);

		System.out.println("response: " + response.getPostCollectionResponse());

		GetNewsReq gnr = new GetNewsReq();
		gnr.setFeed("/test3/");
		Channel ch = aggr.getNews(gnr);

		printChannel(ch);
	}

	private static void putGetDeleteGet(NewsSubsStub subs, NewsAggrStub aggr) throws Exception {

		PutElementRequest per = new PutElementRequest();

		per.setName("/test4");
		per.setUrl("http://www.cs.bgu.ac.il/~gwiener/feed3.xml");

		PutElementResponse peResponse = subs.putElement(per);

		System.out.println("pe response: " + peResponse.getPutElementResponse());

		GetNewsReq gnr = new GetNewsReq();

		gnr.setFeed("/test4");

		Channel ch = aggr.getNews(gnr);

		printChannel(ch);

		DeleteElementRequest der = new DeleteElementRequest();

		der.setName(peResponse.getPutElementResponse());

		DeleteElementResponse deResponse = subs.deleteElement(der);

		System.out.println("deResponse: " + deResponse.getDeleteElementResponse());

		gnr = new GetNewsReq();
		gnr.setFeed("/test4");
		ch = aggr.getNews(gnr);

		printChannel(ch);
	}

	private static void postGetDeleteGet(NewsSubsStub subs, NewsAggrStub aggr) throws Exception{

		PostCollectionRequest pcr = new PostCollectionRequest();

		pcr.setName("/test5/");
		pcr.setUrl("http://dl.dropbox.com/u/28965452/feed4.xml");

		PostCollectionResponse pcResponse = subs.postCollection(pcr);

		System.out.println("response: " + pcResponse.getPostCollectionResponse());

		GetNewsReq gnr = new GetNewsReq();
		gnr.setFeed("/test5/");
		Channel ch = aggr.getNews(gnr);

		printChannel(ch);

		DeleteCollectionRequest dcr = new DeleteCollectionRequest();

		dcr.setName("/test5/");

		DeleteCollectionResponse dcResponse = subs.deleteCollection(dcr);

		System.out.println("\ndeResponse: " + dcResponse.getDeleteCollectionResponse() + "\n");

		gnr = new GetNewsReq();
		gnr.setFeed("/test5/");
		ch = aggr.getNews(gnr);

		printChannel(ch);
	}

	private static void putGetWithFilters(NewsSubsStub subs, NewsAggrStub aggr) throws Exception{

		PutElementRequest per = new PutElementRequest();

		per.setName("/test6");
		per.setUrl("http://www.little-lisper.org/feed1.xml");

		PutElementResponse response = subs.putElement(per);

		System.out.println("response: " + response.getPutElementResponse());

		GetNewsReq gnr = new GetNewsReq();
		gnr.setFeed("/test6");
		gnr.setAuthor("tajel");
		gnr.setCategory("money");
		Channel ch = aggr.getNews(gnr);

		printChannel(ch);

		gnr = new GetNewsReq();
		gnr.setFeed("/test6");
		gnr.setCategory("funny");
		gnr.setTitle("suprise");
		ch = aggr.getNews(gnr);

		printChannel(ch);
	}
}
