package filter;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

public class ItemFilter implements NodeFilter {

	@Override
	public short acceptNode(Node n) {

		if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("item"))
			return FILTER_ACCEPT;

		return FILTER_REJECT;
	}
}
