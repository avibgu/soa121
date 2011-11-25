package feeds;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Feed {

	protected	String				_name;
	protected	String				_url;
	protected	Map<String,Feed>	_feeds;
	protected	Vector<String>		_unnamedFeedsUrls;
	
	public Feed(String name, String url) {
	
		setName(name);
		setUrl(url);
		setNamedFeeds(new HashMap<String,Feed>());
		setUnnamedFeedsUrls(new Vector<String>());
	}

	public void setName(String name) {
		this._name = name;
	}

	public void setUrl(String url) {
		this._url = url;
	}

	public String getUrl() {
		return _url;
	}
	
	public String getName() {
		return _name;
	}

	public void setNamedFeeds(Map<String,Feed> feeds) {
		this._feeds = feeds;
	}

	public Map<String,Feed> getNamedFeeds() {
		return _feeds;
	}
	
	public void setUnnamedFeedsUrls(Vector<String> unnamedFeedsUrls) {
		this._unnamedFeedsUrls = unnamedFeedsUrls;
	}

	public Vector<String> getUnnamedFeedsUrls() {
		return _unnamedFeedsUrls;
	}
	
	public void addSubFeed(String name, Feed feed){
		_feeds.put(name, feed);
	}
}
