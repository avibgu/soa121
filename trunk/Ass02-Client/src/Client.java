import java.rmi.RemoteException;

import org.aggregate.news.NewsAggrStub;
import org.aggregate.news.NewsAggrStub.GetNewsReq;

public class Client {

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {

		NewsAggrStub na = new NewsAggrStub("http://soa2.cs.bgu.ac.il:17171/ex2");
		
		GetNewsReq nr = new GetNewsReq();

		nr.setFeed("http://news.feedzilla.com/en_us/headlines/sports/football-nfl.rss");		
		
		NewsAggrStub.Channel s = na.getNews(nr);
	}
}
