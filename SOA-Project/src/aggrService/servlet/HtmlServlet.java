package aggrService.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HtmlServlet extends HttpServlet {

	private static final long serialVersionUID = -4207940531535920744L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		sendFileResponse(resp, new File("html/index.html"));
	}

	protected void sendFileResponse(HttpServletResponse resp, File file)
			throws IOException {
		PrintWriter printWriter = resp.getWriter();
		
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {

			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			while (br.ready())
				printWriter.println(br.readLine());

			printWriter.flush();

			fis.close();
			isr.close();
			br.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
