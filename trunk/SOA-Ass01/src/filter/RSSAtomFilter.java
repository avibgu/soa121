package filter;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

public class RSSAtomFilter implements NodeFilter {

	private String _whatToFilter;
	
	public RSSAtomFilter(String whatToFilter) {
		_whatToFilter = whatToFilter;
	}
	
	@Override
	public short acceptNode(Node n) {

		if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals(_whatToFilter))
			return FILTER_ACCEPT;

		return FILTER_REJECT;
	}
}
