package server;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.servlet.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

import xml.DOMStreamReader;
import xml.SAXStreamReader;

import feeds.Feed;

public class Main {
	
	public static final int SERVER_PORT = 17171;

	public static void main(String[] args) {
			
		//	startServer();
		
		//	testSAX();
		testDOM1();
		//	testDOM2();
	}
		
	public static void startServer(){
		
		Server server = new Server(SERVER_PORT);

		ServletContextHandler ctx = 
			new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		ctx.setContextPath("/ex1");
		ctx.addServlet(new ServletHolder(new MainServlet(Feed.create(new Vector<String>()))), "/*");
		
//		// Files
//		ResourceHandler res = new ResourceHandler();
//		res.setResourceBase("files");
		
		HandlerList list = new HandlerList();
		
		list.addHandler(ctx);
//		list.addHandler(res);
		
		server.setHandler(list);
		
		try {
			
			server.start();
			server.join();
		}
		catch (Throwable e) {
			
			e.printStackTrace();
		}
	}
	
	public static void testSAX(){
		
		try {
			System.out.println(
				new SAXStreamReader().readFromStream(
					new URL("http://www.cs.bgu.ac.il/~dwss121/Announcements?action=rss")));
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void testDOM1(){
		
		URL url = null;
		
		try {
			url = new URL("http://www.cs.bgu.ac.il/~dwss121/Announcements?action=rss");
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		Map<String,ArrayList<String>> filters = new HashMap<String, ArrayList<String>>();
		
		ArrayList<String> filterValue = new ArrayList<String>();
		filterValue.add("News");
		
		filters.put("category", filterValue);
		
		// Create an executor:
        ExecutorService e = Executors.newFixedThreadPool(7);

        for (int i = 0; i < 7; i++)
        	e.execute(new DOMStreamReader(url, nodes, filters));

        // this causes the executor not to accept any more
        //tasks, and to kill all of its threads when all the
        //submitted tasks are done.
        e.shutdown();
        
        try {
			while (!e.awaitTermination(3, TimeUnit.SECONDS)) continue;
		}
        catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        Transformer transformer = null;
        
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		}
		catch (TransformerConfigurationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

        for (Node node: nodes){
        	
        	Source s = new DOMSource(node);
    		Result r = new StreamResult(System.out);

    		try {
    			transformer.transform(s, r);
    		}
    		catch (TransformerException e3) {
    			// TODO Auto-generated catch block
    			e3.printStackTrace();
    		}
        }
	}

	public static void createDOMDoc() {

		try {

			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			Document d = b.newDocument();
			Element r = d.createElement("dictionary"); 
			
			d.appendChild(r);
			Element w = d.createElement("word");
			r.appendChild(w);
			Element e = d.createElement("update");
			w.appendChild(e);
			e.setAttribute("date","2002-12-24");
			e = d.createElement("name");
			w.appendChild(e);
			e.setAttribute("is_acronym","true");
			e.appendChild(d.createTextNode("DTD"));
			e = d.createElement("definition");
			w.appendChild(e);
			e.appendChild(d.createTextNode("Document Type Definition"));
		}
		catch (ParserConfigurationException e) {
			System.out.println(e.toString()); 	
		}
	}

	public static void testDOM2(){
		
		Node node = new DocumentImpl();
		
        Transformer transformer = null;
        
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		}
		catch (TransformerConfigurationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        	
    	Source s = new DOMSource(node);
		Result r = new StreamResult(System.out);

		try {
			transformer.transform(s, r);
		}
		catch (TransformerException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	}
}   