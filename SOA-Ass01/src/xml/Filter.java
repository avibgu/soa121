package xml;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Filter {
	
	public static Node filterByTitle(Node node, ArrayList<String> filterValues){
		
		return node;
	}
	
	public static Node filterByCategory(Node node, ArrayList<String> filterValues){
		
		for (String filterValue: filterValues){

			if (node instanceof Document){
				
				NodeList categoryNodes = ((Document) node).getElementsByTagName("category");
				
				for (int i = 0; i < categoryNodes.getLength(); i++)
					if (0 == categoryNodes.item(i).getFirstChild().getNodeValue().compareTo(filterValue))
						return node;			
			}
		}
		
		return null;
	}
	
	public static Node filterByAuthor(Node node, ArrayList<String> filterValues){
		
		return node;
	}
	
//	public static Node filter(Node node) {
//	
//		//TODO: currently supports only channel filters.. and not items..
//		
//		boolean found = false;
//		
//		for(	Node child = node.getFirstChild();
//				child != null && !found;
//				child = child.getNextSibling()	){
//	
//			for (String filterKey: getFilters().keySet()){
//				
//				ArrayList<String> values = getFilters().get(filterKey);
//				
//				for (String filterValue: values){
//					
//					if (	0 == child.getNodeName().compareTo(filterKey) &&
//							0 == child.getFirstChild().getNodeValue().compareTo(filterValue)	)
//						found = true;
//					
//					Node tmp = filter(child);
//					
//					if (null != tmp)
//						found = true;
//				}
//			}
//		}
//		
//		if (!found) return null;
//		
//		return node;
//	}
}
