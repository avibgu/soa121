package dbService.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.Post;

public class DBController {

	private Connection mConn;

	private final String mUsername;
	private final String mPassword;

	private final String mDBAddrres;

	private int mLastPostID;

	public DBController() {
		this("", "");
	}

	public DBController(final String pUsername, final String pPassword) {
		this(pUsername, pPassword, "jdbc:mysql://127.0.0.1");
	}

	public DBController(final String pUsername, final String pPassword, final String pDBAddrres) {

		mConn = null;

		mUsername = pUsername;
		mPassword = pPassword;

		mDBAddrres = pDBAddrres;

		mLastPostID = 0;

		createConnection();
	}

	protected void createConnection() {

		try {

			mConn = DriverManager.getConnection(mDBAddrres, mUsername, mPassword);

			// Do something with the Connection

		} catch (final SQLException ex) {

			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public void createNewPost(final Post pPost) {

		final String sqlForPostTable = "insert into posts values (?,?,?,?,?)";
		final String sqlForTagsTable = "insert into tags values (?,?)";

		PreparedStatement pst = null;

		try {

			pst = mConn.prepareStatement(sqlForPostTable);

			pst.setInt(1, mLastPostID);
			pst.setString(2, pPost.getTitle());
			pst.setDate(3, pPost.getDate());
			pst.setString(4, pPost.getContent());
			pst.setString(5, pPost.getAuthor());

			int numOfLinesChanged = pst.executeUpdate();

			System.err.println("inserted to posts: " + numOfLinesChanged + " lines");

			pst.close();

			for (final String tag : pPost.getTags()) {

				pst = mConn.prepareStatement(sqlForTagsTable);

				pst.setInt(1, mLastPostID);
				pst.setString(2, tag);

				numOfLinesChanged = pst.executeUpdate();

				System.err.println("inserted to tags: " + numOfLinesChanged + " lines");

				pst.close();
			}

		} catch (final SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mLastPostID++;
	}

	public ArrayList<Post> getPostsOfSpecificUser(final String pAuthor) throws Exception {
		final ArrayList<Post> result = new ArrayList<Post>();

		final String sqlSelectFromPosts = "SELECT * FROM posts WHERE author = ?";
		final String sqlSelectFromTags = "SELECT * FROM tags WHERE id = ?";

		PreparedStatement pst = null;

		pst = mConn.prepareStatement(sqlSelectFromPosts);

		pst.setString(1, pAuthor);

		final ResultSet rs = pst.executeQuery();

		pst.close();

		while (rs.next()) {

			final int id = rs.getInt(1);
			final String title = rs.getString(2);
			final Date date = rs.getDate(3);
			final String content = rs.getString(4);
			final String author = rs.getString(5);

			final ArrayList<String> tags = new ArrayList<String>();

			pst = mConn.prepareStatement(sqlSelectFromTags);
			pst.setInt(1, id);

			final ResultSet rsFromTags = pst.executeQuery();

			while (rsFromTags.next()) {
				tags.add(rsFromTags.getString(2));
			}

			pst.close();

			final Post post = new Post(title, date, content, author, tags);
			result.add(post);
		}

		return result;

	}

	public ArrayList<Post> getPostsBetweenSpecificDates(final Date pStartDate, final Date pEndDate) {
		// TODO Auto-generated method stub

		return null;

	}

	public ArrayList<Post> getPostsOfTheseTags(final ArrayList<String> arrayList) {
		final ArrayList<Post> result = new ArrayList<Post>();

		return result;
	}
}
