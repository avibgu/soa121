/**
 *	this class gets a url for xml, fetch it, filter the result and
 *         present the final result
 */
package XmlNAtomHandler;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.xml.transform.OutputKeys;
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

/**
 * handles an XML or ATOM file - fetch and filter it.
 *
 * @author niram
 */
public class XmlHandler implements Runnable {

	private String mUrl;
	private Hashtable<String, Vector<String>> filters;
	private Node originalNode;
	private StringBuffer result;
	private int sessionId;

	public XmlHandler(String url, int session,
			Hashtable<String, Vector<String>> filters) {
		this.mUrl = url;
		this.result = new StringBuffer();
		this.filters = filters;
		this.sessionId = session;
	}

	/**
	 *
	 * @param root
	 * @return the node that is a the parent of the items/entries
	 */
	private Node getItemParent(Node root) {
		Node result = root;
		NodeList channelChildes;
		channelChildes = result.getChildNodes();

		// stop condition
		for (int i = 0; i < channelChildes.getLength(); i++) {
			result = channelChildes.item(i);
			if (result.getNodeName().equals("item")
					|| result.getNodeName().equals("entry")) {
				return root;
			}
		}

		for (int i = 0; i < channelChildes.getLength(); i++) {
			result = channelChildes.item(i);
			Node parent = getItemParent(result);
			if (parent != null) {
				return parent;
			}
		}

		return null;
	}

	/**
	 * fetch the node
	 * @throws TransformerException
	 */
	private void getRootNode(){
		if (this.mUrl != null) {
			try {
			TransformerFactory f = TransformerFactory.newInstance();
			Transformer t = f.newTransformer();
			Source s = new StreamSource(mUrl);
			Result r = new DOMResult();
			t.transform(s, r);
			this.originalNode = ((DOMResult) r).getNode();
			} catch (Exception e) {
				this.originalNode = null;
			}
		} else {
			this.originalNode = null;
		}

	}

	// for debug
	private void printResult(Node n) throws TransformerException {
		TransformerFactory f = TransformerFactory.newInstance();
		Transformer t = f.newTransformer();
		t.setOutputProperty(OutputKeys.INDENT, "yes");
		Source s = new DOMSource(n);
		Result r = new StreamResult(System.out);
		t.transform(s, r);
	}

	/**
	 * Main method
	 */
	@Override
	public void run() {

		try {
			this.getRootNode();
			if (this.originalNode != null) {
				ArrayList<Node> items = getItems();
				ArrayList<Node> filterItems = filter(items);
				addFilteredToRes(filterItems);
				addToFinalResult();
			}
			else{
				System.out.println("ERROR in xml handler");
			}
		} catch (Exception e) {
			System.out.println("ERROR in xml handler");
		}
	}

	/**
	 * append local result(items) to final result(rss)
	 */
	private void addToFinalResult() {
		StringBuffer total = XmlOrAtomNode.getXmlInstance(this.sessionId);
		synchronized (total) {
			total.append(result);
		}
	}

	/**
	 * add the filtered items
	 * @param filterItems
	 */
	private void addFilteredToRes(ArrayList<Node> filterItems) {
		for (int i = 0; i < filterItems.size(); i++) {
			Node item = filterItems.get(i);
			this.result.append(this.toString(item));
		}
	}

	/**
	 * filter a group of items
	 * @param items
	 * @return
	 */
	private ArrayList<Node> filter(ArrayList<Node> items) {
		ArrayList<Node> filterItems = new ArrayList<Node>();
		for (int s = 0; s < items.size(); s++) {
			Node item = items.get(s);
			if (filter(item)) {
				filterItems.add(item);
			}
		}
		return filterItems;
	}

	/**
	 * filter a single item
	 * @param item
	 * @return
	 */
	private boolean filter(Node item) {
		NodeList itemChildes = item.getChildNodes();
		Enumeration<String> e = this.filters.keys();
		//iterate through Hashtable keys Enumeration
		while(e.hasMoreElements()){
			String attrName = e.nextElement();
			Vector<String> attrValue = this.filters.get(attrName);

			Node matchedElem = this.getElem(item, attrName);
			if(matchedElem == null){
				return false;
			}
			else{
				for (String attVal : attrValue) {
					if(!attVal.equals(matchedElem.getTextContent())){
						return false;
					}
				}
			}
		}
//		for (int i = 0; i < itemChildes.getLength(); i++) {
//			Node child = itemChildes.item(i);
//			String name = child.getNodeName();
//			Vector<String> filterValue = this.filters.get(name);
//			if (filterValue != null) {
//				String childContent = child.getTextContent();
//				for (String filt : filterValue) {
//					if (!filt.equals(childContent))
//						return false;
//				}
//			}
//		}
		return true;
	}

	/**
	 * get the element match to attrName in item's childes
	 * @param item
	 * @param attrName
	 * @return
	 */
	private Node getElem(Node item, String attrName) {
		NodeList itemChildes = item.getChildNodes();
		for (int i = 0; i < itemChildes.getLength(); i++) {
			Node child = itemChildes.item(i);
			if(child.getNodeName().equals(attrName)){
				// found
				return child;
			}
		}
		// not found
		return null;
	}

	/**
	 * get the items from the original(unfiltered) rss
	 * @return
	 */
	private ArrayList<Node> getItems() {
		Node channel = this.getItemParent(this.originalNode);
		NodeList channelChildes = channel.getChildNodes();
		ArrayList<Node> items = new ArrayList<Node>();
		for (int i = 0; i < channelChildes.getLength(); i++) {
			Node child = channelChildes.item(i);
			if (child.getNodeName().equals("item")
					|| child.getNodeName().equals("entry")) {
				items.add(child);
			}
		}
		return items;
	}

	/**
	 * for DEBUG
	 * @param child
	 * @return string representation of the Node
	 */
	private String toString(Node child) {
		try {
			Transformer t;
			TransformerFactory f = TransformerFactory.newInstance();
			t = f.newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Source s2 = new DOMSource(child);
			Result r2 = new StreamResult(out);
			t.transform(s2, r2);
			String result = out.toString();
			// remove the first line
			result = result.substring(result.indexOf('\n') + 1);
			return result;
		} catch (Exception e) {
			return "";
		}

	}

}
