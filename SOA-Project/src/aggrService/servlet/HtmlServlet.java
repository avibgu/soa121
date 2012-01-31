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

	public HtmlServlet() {
		// DO NOT USE THIS SERVLET.. IT JUST FOR BACKUP..
	}

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getRequestURI().equals("/aggr/scripts.js")) {
			return;
		}
		System.out.println("get");
		System.out.println(req.getRequestURI());
		// System.out.println(req.getRequestURL().toString());
		// this.sendFileResponse(resp, new File("html/index.html"));
		this.sendFileResponse(resp, new File("html/tests.html"));
	}

	protected void sendFileResponse(final HttpServletResponse resp, final File file) throws IOException {
		// System.out.println("got request");
		PrintWriter printWriter = resp.getWriter();

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;

		try {

			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			while (br.ready()) {
				printWriter.println(br.readLine());
			}

			printWriter.flush();

			fis.close();
			isr.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
