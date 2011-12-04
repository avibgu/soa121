package xml;

import java.io.IOException;
import java.io.InputStream;
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
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

import filter.Filter;
import filter.RSSAtomFilter;

public class DOMStreamReader implements Runnable {

	protected	URL						_url;
	protected	ArrayList<Node>			_nodesArray;
	protected	Map<String,String[]>	_filters;
	protected	Transformer				_transformer;
	
	public DOMStreamReader(URL url, ArrayList<Node> nodes, Map<String,String[]> filters) {

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
		
		convertAtomToRSS(node);
		
		if (getFilters() != null && !getFilters().isEmpty())
			node = filter(node);
		
		getNodesArray().add(node);
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
	
	protected void convertAtomToRSS(Node node) {
		
		if (0 == node.getFirstChild().getNodeName().compareTo("rss"))
			return;
		
		if (0 != node.getFirstChild().getNodeName().compareTo("feed"))
			return;

		convertAllTagNames(node, "entry", "item");
		convertAllTagNames(node, "subtitle", "description");
		convertAllTagNames(node, "content", "description");
		convertAllTagNames(node, "summary", "description");
	}
	
	protected void convertAllTagNames(Node node, String from, String to) {

		RSSAtomFilter itemFilter = new RSSAtomFilter(from);
		
		NodeIterator iter;
		
		Node item;
		
		iter = ((DocumentTraversal)node).createNodeIterator(
				node, NodeFilter.SHOW_ELEMENT, itemFilter, false);

		while ((item = iter.nextNode()) != null)
			((Document)node).renameNode(item, item.getParentNode().getNamespaceURI(), to);
			
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

	public void setFilters(Map<String,String[]> filters) {
		this._filters = filters;
	}

	public Map<String,String[]> getFilters() {
		return _filters;
	}
}
