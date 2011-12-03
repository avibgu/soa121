package xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import filter.Filter;

public class DOMStreamReader implements Runnable {

	protected	URL								_url;
	protected	ArrayList<Node>					_nodesArray;
	protected	Map<String,ArrayList<String>>	_filters;
	protected	Transformer						_transformer;
	
	public DOMStreamReader(URL url, ArrayList<Node> nodes, Map<String,ArrayList<String>> filters) {

		setUrl(url);
		setNodesArray(nodes);
		setFilters(filters);
		setTransformer(null);
		
		try {
			setTransformer(TransformerFactory.newInstance().newTransformer());
		}
		catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {

		Node node = readFeedContent();
		
		if (getFilters() != null && !getFilters().isEmpty())
			node = filter(node);
		
		addFeedToNodesList(node);
	}

	protected Node readFeedContent() {

		InputStream inputStream = null;
		
		try {
			inputStream = getUrl().openStream();
		}
		catch (IOException e1) {
			e1.printStackTrace();
			
		}
		
		Source s = new StreamSource(inputStream);
		Result r = new DOMResult();
		
		try {
			getTransformer().transform(s, r);
		}
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ((DOMResult)r).getNode();
	}
	
	protected Node filter(Node node) {

		if (getFilters().containsKey("title"))
			node = Filter.filterItems(node, "title", getFilters().get("title"));
		
		if (getFilters().containsKey("category"))
//			node = Filter.filterByCategory(node, getFilters().get("category"));
			node = Filter.filterItems(node, "category", getFilters().get("category"));
		
		if (getFilters().containsKey("author"))
			node = Filter.filterItems(node, "author", getFilters().get("author"));
			
		return node;
	}
	
	protected void addFeedToNodesList(Node node) {
		
		getNodesArray().add(node);
		
//		for(	Node child = node.getFirstChild();
//				child != null;
//				child = child.getNextSibling()	){
//		
//			getRootNode().appendChild(child);
//		}
	}
	
//	// TODO: should be removed to the FeedsManager..
//	protected void writeFeedContent(Node node) {
//		
//		Source s = new DOMSource(node);
//		Result r = new StreamResult(outputStream);
//
//		synchronized (outputStream) {
//			
//			try {
//				getTransformer().transform(s, r);
//			}
//			catch (TransformerException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	public void setUrl(URL url) {
		this._url = url;
	}

	public URL getUrl() {
		return _url;
	}
	
	public void setNodesArray(ArrayList<Node> nodesArray) {
		this._nodesArray = nodesArray;
	}

	public ArrayList<Node> getNodesArray() {
		return _nodesArray;
	}
	
	public void setTransformer(Transformer transformer) {
		this._transformer = transformer;
	}

	public Transformer getTransformer() {
		return _transformer;
	}

	public void setFilters(Map<String,ArrayList<String>> filters) {
		this._filters = filters;
	}

	public Map<String,ArrayList<String>> getFilters() {
		return _filters;
	}
}
