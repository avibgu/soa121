import org.aggregate.news.NewsAggrStub;


public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		NewsAggrStub na = new NewsAggrStub("cs302six1-1");
		
		
		
		LibraryStub.LoanDoc d = new LibraryStub.LoanDoc();
		LibraryStub.Book_type0 b = new LibraryStub.Book_type0();
		b.setIsbn("1234567890");
		b.setTitle("foo");
		b.setAuthor("bar");
		d.setBook(b);
		LibraryStub.Person_type0 p = new LibraryStub.Person_type0();
		p.setId("0987654321");
		p.setFirstName("John");
		p.setLastName("Doe");
		d.setPerson(d);
		
		
		NewsAggrStub.Channel s = na.getNews(getNewsReq0);
		
	}

}
