package dbService.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import common.Post;

public class DBController {

	private Connection mConn;

	private final String mUsername;
	private final String mPassword;

	private final String mDBAddrres;

	private int mLastPostID;

	public DBController() throws SQLException {
		this("", "");
	}

	public DBController(final String pUsername, final String pPassword) throws SQLException {
		this(pUsername, pPassword, "jdbc:mysql://127.0.0.1:3306");
	}

	public DBController(final String pUsername, final String pPassword, final String pDBAddrres)
			throws SQLException {

		this.mConn = null;

		this.mUsername = pUsername;
		this.mPassword = pPassword;

		this.mDBAddrres = pDBAddrres;

		this.mLastPostID = 0;

		this.createConnection();
	}

	protected void createConnection() throws SQLException {

		try {

			// create the DB is not exist
			this.mConn = DriverManager.getConnection(this.mDBAddrres, "niram", "a");
			final Statement stmt0 = this.mConn.createStatement();
			stmt0.executeUpdate("CREATE DATABASE IF NOT EXISTS testdb");
			this.mConn.close();

			// update connection to automatically connect to that db
			this.mConn = DriverManager.getConnection(this.mDBAddrres + "/testdb", "niram", "a");

			// create the Posts table
			final Statement stmt1 = this.mConn.createStatement();
			final String creationPostsSql = "CREATE TABLE IF NOT EXISTS Posts(" + "id INT  PRIMARY KEY, "
					+ "title VARCHAR(100), " + "date DATE, " + "content VARCHAR(256), "
					+ "author VARCHAR(100))";
			stmt1.executeUpdate(creationPostsSql);

			// create the Tags table
			final Statement stmt2 = this.mConn.createStatement();
			final String creationTagsSql = "CREATE TABLE IF NOT EXISTS Tags(" + "id INT, "
					+ "tag VARCHAR(100),primary key (id, tag),"
					+ "FOREIGN KEY(id) REFERENCES Posts(Id) ON DELETE CASCADE)";
			stmt2.executeUpdate(creationTagsSql);

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
			pst.setDate(3, pPost.getDate());
			pst.setString(4, pPost.getContent());
			pst.setString(5, pPost.getAuthor());

			int numOfLinesChanged = pst.executeUpdate();

			System.err.println("inserted to posts: " + numOfLinesChanged + " lines");

			pst.close();

			for (final String tag : pPost.getTags()) {

				pst = this.mConn.prepareStatement(sqlForTagsTable);

				pst.setInt(1, this.mLastPostID);
				pst.setString(2, tag);

				numOfLinesChanged = pst.executeUpdate();

				System.err.println("inserted to tags: " + numOfLinesChanged + " lines");

				pst.close();
			}

		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.mLastPostID++;
	}

	public ArrayList<Post> getPostsOfSpecificUser(final String pAuthor) throws Exception {

		final String sqlSelectFromPosts = "SELECT * FROM Posts WHERE author = ?";

		PreparedStatement pst = null;

		pst = this.mConn.prepareStatement(sqlSelectFromPosts);

		pst.setString(1, pAuthor);

		final ResultSet rs = pst.executeQuery();

		pst.close();

		final ArrayList<Post> result = this.postsFromResultSet(rs);

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
			final Date date = rs.getDate(3);
			final String content = rs.getString(4);
			final String author = rs.getString(5);

			final ArrayList<String> tags = new ArrayList<String>();

			final String sqlSelectFromTags = "SELECT tag FROM Tags WHERE id = ?";
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
	public ArrayList<Post> getPostsBetweenSpecificDates(final Date pStartDate, final Date pEndDate)
			throws SQLException {
		final String sqlSelectFromPosts = "SELECT * FROM Posts WHERE date <= ? AND date => ?";

		PreparedStatement pst = null;

		pst = this.mConn.prepareStatement(sqlSelectFromPosts);

		pst.setDate(1, pStartDate);
		pst.setDate(2, pEndDate);

		final ResultSet rs = pst.executeQuery();

		pst.close();

		final ArrayList<Post> result = this.postsFromResultSet(rs);

		return result;

	}

	/**
	 * 
	 * @param pTags
	 * @return the posts who have at least on tag from pTags
	 * @throws SQLException
	 */
	public ArrayList<Post> getPostsOfTheseTags(final ArrayList<String> pTags) throws SQLException {
		final Set<Post> resultSet = new HashSet<Post>();

		// get the posts' ids who have at least on tag from pTags
		final String sqlSelectIdFromTags = "SELECT id FROM Tags WHERE tag = ?";
		// the posts' ids who have at least on tag from pTags
		final Set<Integer> ids = new HashSet<Integer>();
		for (final String tag : pTags) {
			final PreparedStatement pst = this.mConn.prepareStatement(sqlSelectIdFromTags);

			pst.setString(1, tag);
			final ResultSet resultIds = pst.executeQuery();
			pst.close();

			while (resultIds.next()) {
				ids.add(resultIds.getInt(1));
			}
		}

		// get the posts according to the ids
		final String sqlSelectIdFromPosts = "SELECT * FROM Posts WHERE id = ?";
		for (final Integer id : ids) {
			final PreparedStatement pst = this.mConn.prepareStatement(sqlSelectIdFromPosts);

			pst.setInt(1, id);
			final ResultSet resultPosts = pst.executeQuery();
			pst.close();
			resultSet.addAll(this.postsFromResultSet(resultPosts));

		}

		final ArrayList<Post> result = new ArrayList<Post>(resultSet);
		return result;
	}
}
