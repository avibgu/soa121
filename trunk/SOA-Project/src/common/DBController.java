package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * TODO: check SQL injection!
 * 
 * @author nir
 * 
 */
public class DBController {

	private Connection mConn;
	private final String mUsername;
	private final String mPassword;
	private final String mDBAddrres;
	private int mLastPostID;

	// the number of tires until reaching a threshold failure
	private int numOfTriesTillFailures = 0;
	private final int failureThreshold = 10000;

	public DBController() {
		this("", "");
	}

	public DBController(final String pUsername, final String pPassword) {
		this(pUsername, pPassword, "jdbc:mysql://127.0.0.1:3306");
		// this(pUsername, pPassword, "jdbc:mysql://db4free.net:3306");
	}

	public DBController(final String pUsername, final String pPassword, final String pDBAddrres) {

		this.numOfTriesTillFailures = 0;

		this.mConn = null;
		this.mUsername = pUsername;
		this.mPassword = pPassword;
		this.mDBAddrres = pDBAddrres;

		this.mLastPostID = 0;

		try {
			this.createConnection();
			// TODO: commet this line in the end of tests
			// new TestDBController(this).testPost();
		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void createConnection() throws SQLException {

		try {
			final String schema = "testdb";
			// final String schema = "soa121db";

			// create the DB is not exist
			this.mConn = DriverManager.getConnection(this.mDBAddrres, "niram", "a");
			// this.mConn = DriverManager.getConnection(this.mDBAddrres, schema,
			// schema);
			final Statement stmt0 = this.mConn.createStatement();
			stmt0.executeUpdate("CREATE DATABASE IF NOT EXISTS " + schema);
			this.mConn.close();

			// update connection to automatically connect to that db
			this.mConn = DriverManager.getConnection(this.mDBAddrres + "/" + schema, "niram", "a");
			// this.mConn = DriverManager.getConnection(this.mDBAddrres + "/" +
			// schema, schema, schema);

			// create the Posts table
			final Statement stmt1 = this.mConn.createStatement();
			final String creationPostsSql = "CREATE TABLE IF NOT EXISTS Posts(" + "postId INT  PRIMARY KEY, "
					+ "title VARCHAR(100), " + "date datetime, " + "content VARCHAR(256), "
					+ "author VARCHAR(100))";
			stmt1.executeUpdate(creationPostsSql);

			// create the Tags table
			final Statement stmt2 = this.mConn.createStatement();
			final String creationTagsSql = "CREATE TABLE IF NOT EXISTS Tags(" + "postId INT, "
					+ "tag VARCHAR(100),primary key (postId, tag),"
					+ "FOREIGN KEY(postId) REFERENCES Posts(postId) ON DELETE CASCADE)";
			stmt2.executeUpdate(creationTagsSql);

			// update the last post id - to be persistence
			final Statement st = this.mConn.createStatement();
			final ResultSet res = st.executeQuery("SELECT MAX(postId) FROM Posts");
			while (res.next()) {
				this.mLastPostID = res.getInt(1);
			}
			this.mLastPostID++;
			System.out.println(this.mLastPostID + " after max");
			// System.out.println("Number of column: " + this.mLastPostID);

		} catch (final SQLException ex) {

			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			throw ex;
		}
	}

	public void createNewPost(final Post pPost) {
		final String sqlForPostTable = "insert into Posts values (?,?,?,?,?)";
		final String sqlForTagsTable = "insert into Tags values (?,?)";

		PreparedStatement pst = null;

		try {

			pst = this.mConn.prepareStatement(sqlForPostTable);

			pst.setInt(1, this.mLastPostID);
			pst.setString(2, pPost.getTitle());
			pst.setTimestamp(3, pPost.getDate());
			pst.setString(4, pPost.getContent());
			pst.setString(5, pPost.getAuthor());

			int numOfLinesChanged = pst.executeUpdate();

			System.out.println("inserted to posts: " + numOfLinesChanged + " lines");

			pst.close();

			for (final String tag : pPost.getTags()) {

				pst = this.mConn.prepareStatement(sqlForTagsTable);

				pst.setInt(1, this.mLastPostID);
				pst.setString(2, tag);

				numOfLinesChanged = pst.executeUpdate();
				System.out.println("inserted to tags: " + numOfLinesChanged + " lines");

				pst.close();
			}

		} catch (final SQLException e) {
			System.out.println("Exception in createNewPost, trying with new id (maybe because of delete)");
			e.printStackTrace();
			if (this.numOfTriesTillFailures < this.failureThreshold) {
				this.numOfTriesTillFailures++;
				this.mLastPostID++;
				this.createNewPost(pPost);
				return;
			}
		}

		this.mLastPostID++;
		this.numOfTriesTillFailures = 0;
	}

	public ArrayList<Post> getPostsOfSpecificAuthor(final String pAuthor) throws Exception {

		final String sqlSelectFromPosts = "SELECT * FROM Posts WHERE author = ?";

		PreparedStatement pst = null;

		pst = this.mConn.prepareStatement(sqlSelectFromPosts);
		pst.setString(1, pAuthor);
		final ResultSet rs = pst.executeQuery();

		final ArrayList<Post> result = this.postsFromResultSet(rs);
		System.out.println("result = " + result.size());
		pst.close();

		return result;

	}

	/**
	 * @param rs
	 * @return the posts matching to the id's in the result set
	 * @throws SQLException
	 */
	private ArrayList<Post> postsFromResultSet(final ResultSet rs) throws SQLException {
		PreparedStatement pst;
		final ArrayList<Post> result = new ArrayList<Post>();
		while (rs.next()) {

			final int id = rs.getInt(1);
			final String title = rs.getString(2);
			final Timestamp date = rs.getTimestamp(3);
			final String content = rs.getString(4);
			final String author = rs.getString(5);

			final ArrayList<String> tags = new ArrayList<String>();

			final String sqlSelectFromTags = "SELECT tag FROM Tags WHERE postId = ?";
			pst = this.mConn.prepareStatement(sqlSelectFromTags);
			pst.setInt(1, id);
			final ResultSet rsFromTags = pst.executeQuery();

			while (rsFromTags.next()) {
				tags.add(rsFromTags.getString(1));
			}

			pst.close();

			final Post post = new Post(title, date, content, author, tags);
			result.add(post);
		}
		return result;
	}

	/**
	 * 
	 * @param pStartDate
	 * @param pEndDate
	 * @return the posts who's publishing date is between pStartDate and
	 *         pEndDate
	 * @throws SQLException
	 */
	public ArrayList<Post> getPostsBetweenSpecificDates(final Timestamp pStartDate, final Timestamp pEndDate)
			throws SQLException {
		final String sqlSelectFromPosts = "SELECT * FROM Posts WHERE date BETWEEN ? AND ?";

		PreparedStatement pst = null;

		pst = this.mConn.prepareStatement(sqlSelectFromPosts);

		pst.setTimestamp(1, pStartDate);
		pst.setTimestamp(2, pEndDate);

		final ResultSet rs = pst.executeQuery();

		final ArrayList<Post> result = this.postsFromResultSet(rs);

		pst.close();

		return result;

	}

	/**
	 * 
	 * @param pTags
	 * @return the posts who have at least on tag from pTags
	 * @throws SQLException
	 */
	public ArrayList<Post> getPostsOfTheseTags(final String[] pTags) throws SQLException {
		if (pTags == null) {
			// if there is no tags
			final String sqlPostsWithNoTags = "SELECT posts.* FROM posts LEFT JOIN tags "
					+ "ON posts.postId = tags.postId WHERE tags.postid IS NULL";

			final PreparedStatement pst = this.mConn.prepareStatement(sqlPostsWithNoTags);
			final ResultSet postsWithNoTags = pst.executeQuery();
			return this.postsFromResultSet(postsWithNoTags);
		} else {
			final Set<Post> resultSet = new HashSet<Post>();
			// get the posts' ids who have at least on tag from pTags
			final String sqlSelectIdFromTags = "SELECT postId FROM Tags WHERE tag = ?";
			// the posts' ids who have at least on tag from pTags
			final Set<Integer> ids = new HashSet<Integer>();
			for (final String tag : pTags) {
				final PreparedStatement pst = this.mConn.prepareStatement(sqlSelectIdFromTags);

				pst.setString(1, tag);
				final ResultSet resultIds = pst.executeQuery();

				while (resultIds.next()) {
					ids.add(resultIds.getInt(1));
				}

				pst.close();

			}

			// get the posts according to the ids
			final String sqlSelectIdFromPosts = "SELECT * FROM Posts WHERE postId = ?";
			for (final Integer id : ids) {
				final PreparedStatement pst = this.mConn.prepareStatement(sqlSelectIdFromPosts);

				pst.setInt(1, id);
				final ResultSet resultPosts = pst.executeQuery();
				resultSet.addAll(this.postsFromResultSet(resultPosts));
				pst.close();
			}

			final ArrayList<Post> result = new ArrayList<Post>(resultSet);
			return result;
		}
	}
}
