package dbService.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBController {

	private Connection mConn;

	private String mUsername;
	private String mPassword;

	private String mDBAddrres;

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
	}

	public void createConnection() {

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
}
