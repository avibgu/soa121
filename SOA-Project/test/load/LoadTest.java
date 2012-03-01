package load;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class LoadTest {

	public static final int NUM_OF_TESTS = 1000;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws Exception {
		System.out.println("Start Time: "
				+ new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString());

		for (int i = 0; i < NUM_OF_TESTS; i++) {
			if (Math.random() < 0.5) {
				this.sendGetMessage();
			} else {
				this.sendPostMessageWithData();
			}
		}

		System.out.println("End Time: "
				+ new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()).toString());
	}

	protected void sendGetMessage() throws MalformedURLException, IOException {
		URL aggrServerUrl = new URL("http://127.0.0.1:17172/aggr/?author=avi");
		HttpURLConnection conn = (HttpURLConnection) aggrServerUrl.openConnection();

		conn.setDoOutput(true);
		conn.setDoInput(true);

		conn.addRequestProperty("User-Agent", "Mozilla/5.0");

		conn.connect();

		BufferedReader inBuff = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		String inputLine = "";
		String respone = "";

		while ((inputLine = inBuff.readLine()) != null) {
			respone += inputLine;
		}

		// System.out.println(respone);

		conn.disconnect();
	}

	protected void sendPostMessageWithData() throws IOException {
		// URL subsServerUrl = new
		// URL("http://127.0.0.1:17172/aggr/?author=avi");
		String targetURL = "http://127.0.0.1:17171";
		// String urlParameters = "";

		String username;
		if (Math.random() < 0.5) {
			username = "Test User Name 1";
		} else {
			username = "Test User Name 2";
		}

		String title = "Test Title";
		String content = "Test Content";
		String jTags = "\"\"";

		String post = "{\"title\":\"" + title + "\"," + "\"author\":\"" + username + "\"," + "\"tags\":"
				+ jTags + "," + "\"content\":\"" + content + "\"}";

		// System.out.println("Sending post msg with:\n" + post);
		this.sendPostMessageWithData(targetURL, post);

	}

	protected void sendPostMessageWithData(final String targetURL, final String urlParameters) {

		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			connection.addRequestProperty("User-Agent", "Mozilla/5.0");

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			// InputStream is = connection.getInputStream();
			// BufferedReader rd = new BufferedReader(new
			// InputStreamReader(is));
			// String line;
			// StringBuffer response = new StringBuffer();
			// while ((line = rd.readLine()) != null) {
			// response.append(line);
			// response.append('\r');
			// }
			// rd.close();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}
