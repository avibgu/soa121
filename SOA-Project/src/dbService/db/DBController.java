package dbService.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import common.Post;

public class DBController {

	private Connection mConn;

	private String mUsername;
	private String mPassword;

	private String mDBAddrres;

	private int mLastPostID;

	public DBController() {
		this("","");
	}

	public DBController(String pUsername, String pPassword) {
		this(pUsername, pPassword, "jdbc:mysql://127.0.0.1");
	}

	public DBController(String pUsername, String pPassword, String pDBAddrres) {

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


		} catch (SQLException ex) {

			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public void createNewPost(Post pPost) {

		String sqlForPostTable = "insert into posts values (?,?,?,?,?)";
		String sqlForTagsTable = "insert into tags values (?,?)";

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

			for (String tag : pPost.getTags()) {

				pst = mConn.prepareStatement(sqlForTagsTable);

				pst.setInt(1, mLastPostID);
				pst.setString(2, tag);

				numOfLinesChanged = pst.executeUpdate();

				System.err.println("inserted to tags: " + numOfLinesChanged + " lines");

				pst.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mLastPostID++;
	}
}
