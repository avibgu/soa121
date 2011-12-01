package xml;

import java.util.ArrayList;

import org.w3c.dom.Node;

public class Filter {
	
	public static Node filterByTitle(Node node, ArrayList<String> filterValues){
		
		return node;
	}
	
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
			
			//	TODO: getElementsByTagName
		}
		
		if (!found) return null;
		
		return node;
	}
	
	public static Node filterByAuthor(Node node, ArrayList<String> filterValues){
		
		return node;
	}
	
//	public static Node filter(Node node) {
//	
//	//TODO: currently supports only channel filters.. and not items..
//	
//	boolean found = false;
//	
//	for(	Node child = node.getFirstChild();
//			child != null && !found;
//			child = child.getNextSibling()	){
//
//		for (String filterKey: getFilters().keySet()){
//			
//			ArrayList<String> values = getFilters().get(filterKey);
//			
//			for (String filterValue: values){
//				
//				if (	0 == child.getNodeName().compareTo(filterKey) &&
//						0 == child.getFirstChild().getNodeValue().compareTo(filterValue)	)
//					found = true;
//				
//				Node tmp = filter(child);
//				
//				if (null != tmp)
//					found = true;
//			}
//		}
//	}
//	
//	if (!found) return null;
//	
//	return node;
//}
}
