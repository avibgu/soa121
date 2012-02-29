package load;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class LoadTest {

	public static final int NUM_OF_TESTS = 1000;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() throws Exception {

		for (int i = 0; i < NUM_OF_TESTS; i++)
			sendMessage();		
	}

	protected void sendMessage() throws MalformedURLException, IOException {
		URL aggrServerUrl = new URL("http://127.0.0.1:17172/aggr/?author=avi");
		HttpURLConnection conn = (HttpURLConnection) aggrServerUrl.openConnection();
		
		conn.setDoOutput(true);
		conn.setDoInput(true);
		
		conn.addRequestProperty("User-Agent", "Mozilla/5.0");
		
		conn.connect();

		BufferedReader inBuff = new BufferedReader( new InputStreamReader( conn.getInputStream()));
		
		String inputLine = "";
		String respone = "";

		while ((inputLine = inBuff.readLine()) != null) respone += inputLine;
		
		System.out.println(respone);
		
		conn.disconnect();
	}
}
