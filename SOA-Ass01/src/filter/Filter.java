package filter;

import java.util.ArrayList;

import org.w3c.dom.Node;

import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;


public class Filter {
	
	public static Node filterByCategory(Node node, ArrayList<String> filterValues){
		
		//TODO: currently supports only channel filters.. and not items..
		
		boolean found = false;
		
		for(	Node child = node.getFirstChild();
				child != null && !found;
				child = child.getNextSibling()	){
				
			for (String filterValue: filterValues){
				
				if (	0 == child.getNodeName().compareTo("category") &&
						0 == child.getFirstChild().getNodeValue().compareTo(filterValue)	)
					found = true;
				
				if (found) break;
				
				if (null != filterByCategory(child, filterValues))
					found = true;
			}
		}
		
		if (!found) return null;
		
		return node;
	}
	
	public static Node filterItems(Node node, String itemPropertyToFilter,
			String[] filterValues){
		
		RSSAtomFilter itemFilter = new RSSAtomFilter("item");
		
		NodeIterator iter;
		
		Node item;
		
		for (String filterValue: filterValues){
			
			iter = ((DocumentTraversal)node).createNodeIterator(
					node, NodeFilter.SHOW_ELEMENT, itemFilter, false);

			while ((item = iter.nextNode()) != null)
				removeItemThatDoesntMatchTheFilter(item, itemPropertyToFilter, filterValue);
		}
		
		return node;
	}
	
	public static boolean removeItemThatDoesntMatchTheFilter(Node item,
			String filterKey, String filterValue) {

		String filterValueWithoutNL = filterValue.trim();
		
		boolean found = false;
		   
		for (	Node child = item.getFirstChild(); 
				child != null && !found;
				child = child.getNextSibling()){

			if(0 == child.getNodeName().compareTo(filterKey)){
				
				String nodeValue = child.getFirstChild().getNodeValue();
			
				if (	null != nodeValue &&
						(0 == nodeValue.compareTo(filterValue) ||
						 0 == nodeValue.compareTo(filterValueWithoutNL))){

					found = true;
				}
			}
		}
		
		if (!found)
			item.getParentNode().removeChild(item);
		
		return found;
	}
}
