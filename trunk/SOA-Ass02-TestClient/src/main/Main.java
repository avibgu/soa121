package main;

import java.rmi.RemoteException;

import org.aggregate.news.NewsAggrStub;
import org.aggregate.news.NewsAggrStub.GetNewsReq;
import org.subscription.news.NewsSubsStub;
import org.subscription.news.NewsSubsStub.PostCollectionRequest;

public class Main {

	public static void main(String[] args) throws RemoteException {

		NewsSubsStub subs = new NewsSubsStub();

		PostCollectionRequest pcr = new PostCollectionRequest();

		pcr.setName("test3");
		pcr.setUrl("http://www.cs.bgu.ac.il/~gwiener/feed3.xml");

		subs.postCollection(pcr);

		NewsAggrStub aggr = new NewsAggrStub();

		GetNewsReq gnr = new GetNewsReq();

		gnr.setFeed("test3");

		System.out.println(aggr.getNews(gnr));
	}
}
