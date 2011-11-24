package feeds;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;

public class FeedsManagerTest {

	FeedsManager _fm;
	
	@Before
	public void setUp() throws Exception {
		
		_fm = new FeedsManager();
	}

	@Test
	public void putTest() throws BadRequestException, NotImplaementedException{
		
		assertEquals(0, _fm.getNumOfFeeds());
		
		_fm.putNamedFeed(
			"/ex1/ynet/news",
			"http://www.ynet.co.il/Integration/StoryRss2.xml");
		
		assertEquals(1, _fm.getNumOfFeeds());
	}
	
	@Test
	public void postTest() throws BadRequestException, NotImplaementedException{
		
		assertEquals(0, _fm.getNumOfFeeds());
		
		_fm.postUnnamedFeed(
			"/ex1/ynet/",
			"http://www.ynet.co.il/Integration/StoryRss3052.xml");
		
		assertEquals(1, _fm.getNumOfFeeds());
	}
	
	@Test
	public void deleteTest() throws BadRequestException, NotImplaementedException{
		
		assertEquals(0, _fm.getNumOfFeeds());
		
		_fm.putNamedFeed(
				"/ex1/ynet/env",
				"http://www.ynet.co.il/Integration/StoryRss4879.xml");

		assertEquals(1, _fm.getNumOfFeeds());
		
		_fm.deleteFeeds("/ex1/ynet/env");
		
		assertEquals(0, _fm.getNumOfFeeds());
	}
	
	@Test
	public void getTest() throws BadRequestException, NotImplaementedException{
		
		assertEquals(0, _fm.getNumOfFeeds());
		
		_fm.putNamedFeed(
			"/ex1/ynet/news",
			"http://www.ynet.co.il/Integration/StoryRss2.xml");
		
		assertEquals(1, _fm.getNumOfFeeds());
		
		_fm.postUnnamedFeed(
			"/ex1/ynet/",
			"http://www.ynet.co.il/Integration/StoryRss3052.xml");
		
		assertEquals(2, _fm.getNumOfFeeds());
		
		Map<String, String> filters = new HashMap<String, String>(); 
		
		filters.put("category", "special");
		
		_fm.getFeeds("/ex1/ynet", filters);
	}
}
