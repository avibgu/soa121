package common;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class TestDBController {

	private final DBController mDBController;

	public TestDBController(final DBController pDBController) {
		this.mDBController = pDBController;
	}

	/**
	 * main test method
	 */
	public void testPost() {
		// test for adding new posts
		// this.createPostsForTest();
		// this.createPost("title2", "contnet1", "author", new
		// ArrayList<String>());

		// test getters with filters
		this.testGetWwithFilters();
	}

	/**
	 * test getters with filters
	 */
	private void testGetWwithFilters() {
		try {
			// test get post by author
			System.out.println(Arrays.toString(this.mDBController.getPostsOfSpecificAuthor("author0")
					.toArray()));
			System.out.println(Arrays.toString(this.mDBController.getPostsOfSpecificAuthor("author1")
					.toArray()));
			System.out.println(Arrays.toString(this.mDBController.getPostsOfSpecificAuthor("author3")
					.toArray()));
			System.out.println(Arrays.toString(this.mDBController.getPostsOfSpecificAuthor("noName")
					.toArray()));
			System.out.println(Arrays.toString(this.mDBController.getPostsOfSpecificAuthor("").toArray()));
			System.out.println(Arrays.toString(this.mDBController.getPostsOfSpecificAuthor(null).toArray()));

			// check get with tags
			String[] emptyTags = null;
			System.out.println(Arrays.toString(this.mDBController.getPostsOfTheseTags(emptyTags).toArray()));

			// check for one existing tag
			String[] oneExistingTag = new String[4];
			oneExistingTag[0] = "se";
			System.out.println(Arrays.toString(this.mDBController.getPostsOfTheseTags(oneExistingTag)
					.toArray()));

			String[] oneExistingOneNotTag = new String[4];
			oneExistingOneNotTag[0] = "se";
			oneExistingOneNotTag[1] = "no";
			System.out.println(Arrays.toString(this.mDBController.getPostsOfTheseTags(oneExistingOneNotTag)
					.toArray()));

			String[] oneExistingOneNullTag = new String[4];
			oneExistingOneNullTag[0] = "se";
			oneExistingOneNullTag[1] = null;
			System.out.println(Arrays.toString(this.mDBController.getPostsOfTheseTags(oneExistingOneNullTag)
					.toArray()));

			String[] oneCapitalLetterTag = new String[4];
			oneCapitalLetterTag[0] = "SE";
			System.out.println(Arrays.toString(this.mDBController.getPostsOfTheseTags(oneCapitalLetterTag)
					.toArray()));

			System.out.println(Arrays.toString(this.mDBController.getPostsBetweenSpecificDates(
					new Timestamp(1), java.sql.Timestamp.valueOf("2011-01-01 09:01:10")).toArray()));

			System.out.println(Arrays.toString(this.mDBController.getPostsBetweenSpecificDates(
					java.sql.Timestamp.valueOf("2015-01-01 09:01:10"),
					java.sql.Timestamp.valueOf("2013-01-01 09:01:10")).toArray()));

			System.out.println(Arrays.toString(this.mDBController.getPostsBetweenSpecificDates(
					java.sql.Timestamp.valueOf("2015-01-01 09:01:10"),
					java.sql.Timestamp.valueOf("2008-01-01 09:01:10")).toArray()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createPost(final String title, final String content, final String author,
			final ArrayList<String> tags) {
		Post post = new Post(title, new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()),
				content, author, tags);
		this.mDBController.createNewPost(post);

	}

	/**
	 * create fake posts for tests
	 */
	private void createPostsForTest() {
		ArrayList<String> tags = new ArrayList<String>();
		tags.add("sport");
		tags.add("se");
		Post noTitle = new Post("", new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()),
				"noTitle", "author0", tags);
		this.mDBController.createNewPost(noTitle);

		Post noAuthor = new Post("title1", new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()),
				"noAuthor", "", tags);
		this.mDBController.createNewPost(noAuthor);

		Post noContent = new Post("noContent", new java.sql.Timestamp(Calendar.getInstance()
				.getTimeInMillis()), "", "author1", tags);
		this.mDBController.createNewPost(noContent);

		ArrayList<String> tags1 = new ArrayList<String>();
		tags.add("sport");
		tags.add("se");
		Post multyLine = new Post("title1", new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()),
				"line1\nline2", "author1", tags1);
		this.mDBController.createNewPost(multyLine);

		ArrayList<String> tags2 = new ArrayList<String>();
		tags.add("sport");
		tags.add("se");
		Post regular = new Post("title1", new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()),
				"regular", "author3", tags2);
		this.mDBController.createNewPost(regular);

		Post noTags = new Post("title1", java.sql.Timestamp.valueOf("2010-01-01 09:01:10"),
				"noTagsDateEarly", "author3", new ArrayList<String>());
		this.mDBController.createNewPost(noTags);

		Post identicalPosts1 = new Post("title1", new java.sql.Timestamp(Calendar.getInstance()
				.getTimeInMillis()), "identicalPosts1", "author3", new ArrayList<String>());
		this.mDBController.createNewPost(identicalPosts1);

		Post identicalPosts2 = new Post("title1", new java.sql.Timestamp(Calendar.getInstance()
				.getTimeInMillis()), "identicalPosts2", "author3", new ArrayList<String>());
		this.mDBController.createNewPost(identicalPosts2);
	}

}
