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

			// putGet(subs, aggr);
			// postGet(subs, aggr);
			// putGetDeleteGet(subs, aggr);
			// postGetDeleteGet(subs, aggr);
			// putGetWithFilters(subs, aggr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printChannel(Channel ch) {

		int i = 1;

		for (Item_type0 item : ch.getItem()) {

			System.out.println("ITEM " + i++ + ":");

			System.out.println("title:       " + item.getTitle());
			System.out.println("author:      " + item.getAuthor());
			System.out.println("category:    " + item.getCategory());
			System.out.println("description: " + item.getDescription() + "\n");
		}
	}

	private static void putGet(NewsSubsStub subs, NewsAggrStub aggr)
			throws Exception {

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

	private static void postGet(NewsSubsStub subs, NewsAggrStub aggr)
			throws Exception {

		PostCollectionRequest pcr = new PostCollectionRequest();

		pcr.setName("/test3/");
		pcr.setUrl("http://dl.dropbox.com/u/28965452/feed4.xml");

		PostCollectionResponse response = subs.postCollection(pcr);

		System.out.println("response: " + response.getPostCollectionResponse());

		GetNewsReq gnr = new GetNewsReq();
		gnr.setFeed("/test3/");
		Channel ch = aggr.getNews(gnr);
		System.out.println("before");
		printChannel(ch);
		System.out.println("End");

	}

	private static void putGetDeleteGet(NewsSubsStub subs, NewsAggrStub aggr)
			throws Exception {

		PutElementRequest per = new PutElementRequest();

		per.setName("/test4");
		per.setUrl("http://www.cs.bgu.ac.il/~gwiener/feed3.xml");

		PutElementResponse peResponse = subs.putElement(per);

		System.out
				.println("pe response: " + peResponse.getPutElementResponse());

		GetNewsReq gnr = new GetNewsReq();

		gnr.setFeed("/test4");

		Channel ch = aggr.getNews(gnr);

		printChannel(ch);
		System.out.println("\n\n--------------------------\n\n");
		DeleteElementRequest der = new DeleteElementRequest();

		der.setName(peResponse.getPutElementResponse());

		DeleteElementResponse deResponse = subs.deleteElement(der);

		System.out.println("deResponse: "
				+ deResponse.getDeleteElementResponse());

		gnr = new GetNewsReq();
		gnr.setFeed("/test4");
		ch = aggr.getNews(gnr);

		printChannel(ch);
	}

	private static void postGetDeleteGet(NewsSubsStub subs, NewsAggrStub aggr)
			throws Exception {

		PostCollectionRequest pcr = new PostCollectionRequest();

		pcr.setName("/test5/");
		pcr.setUrl("http://dl.dropbox.com/u/28965452/feed4.xml");

		PostCollectionResponse pcResponse = subs.postCollection(pcr);

		System.out.println("response: "
				+ pcResponse.getPostCollectionResponse());

		GetNewsReq gnr = new GetNewsReq();
		gnr.setFeed("/test5/");
		Channel ch = aggr.getNews(gnr);

		printChannel(ch);

		DeleteCollectionRequest dcr = new DeleteCollectionRequest();

		dcr.setName("/test5/");

		DeleteCollectionResponse dcResponse = subs.deleteCollection(dcr);

		System.out.println("\ndeResponse: "
				+ dcResponse.getDeleteCollectionResponse() + "\n");

		gnr = new GetNewsReq();
		gnr.setFeed("/test5/");
		ch = aggr.getNews(gnr);

		printChannel(ch);
	}

	private static void putGetWithFilters(NewsSubsStub subs, NewsAggrStub aggr)
			throws Exception {

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
		System.out.println("\n\n---------------------\n\n");

		gnr = new GetNewsReq();
		gnr.setFeed("/test6");
		gnr.setCategory("funny");
		gnr.setTitle("suprise");
		ch = aggr.getNews(gnr);

		printChannel(ch);
	}

	// b/
	// /b/a
	// print everything individually
	private static void testElementInFile(NewsSubsStub subs, NewsAggrStub aggr)
			throws Exception {

		PostCollectionRequest pcr = new PostCollectionRequest();
		pcr.setName("/b/");
		PostCollectionResponse pcResponse = subs.postCollection(pcr);
		// System.out.println("response: "
		// + pcResponse.getPostCollectionResponse());

		PutElementRequest per2 = new PutElementRequest();
		per2.setName("/b/a");
		per2.setUrl("http://www.little-lisper.org/feed1.xml");
		PutElementResponse response2 = subs.putElement(per2);

		System.out.println("b:");
		GetNewsReq gnr = new GetNewsReq();
		gnr.setFeed(pcResponse.getPostCollectionResponse());
		Channel ch = aggr.getNews(gnr);
		printChannel(ch);
		System.out.println("\n\n---------------------\n\n");

		System.out.println("/b/a");
		GetNewsReq gnr2 = new GetNewsReq();
		gnr2.setFeed(response2.getPutElementResponse());
		Channel ch2 = aggr.getNews(gnr2);
		printChannel(ch2);
		System.out.println("\n\n---------------------\n\n");

	}

	// /a
	// /b/
	// /b/a
	// prints each a (make different feeds)
	private static void test1(NewsSubsStub subs, NewsAggrStub aggr)
			throws Exception {
	}

	// /b/
	// /b/a with feed 1
	// /b/c with feed 1
	// print get on b with filters ant check that each item appears twice
	private static void test2(NewsSubsStub subs, NewsAggrStub aggr)
			throws Exception {
	}

	// /a/
	// /a/b/
	// /a/b/c
	// (make different feeds)
	// delete /a/b
	// check that a and only a stay
	private static void test3(NewsSubsStub subs, NewsAggrStub aggr)
			throws Exception {
	}

	// /a
	// /b/a
	// delete /a
	// check that the other a stays
	private static void test4(NewsSubsStub subs, NewsAggrStub aggr)
			throws Exception {
	}

	// /a
	// /b/a
	// delete /b/a
	// check that the other a stays
	private static void test5(NewsSubsStub subs, NewsAggrStub aggr)
			throws Exception {
	}

}
