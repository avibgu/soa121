package feeds;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Feed {
	protected	URL					_url;
	protected	Map<String,Feed>	_namedFeeds;
	protected	Vector<URL>		_unnamedFeedsUrls;

	public Feed() {
		setNamedFeeds(new HashMap<String,Feed>());
		setUnnamedFeedsUrls(new Vector<URL>());
	}

	public void setUrl(URL url) {
		this._url = url;
	}

	public URL getUrl() {
		return _url;
	}

	public void setNamedFeeds(Map<String,Feed> feeds) {
		this._namedFeeds = feeds;
	}

	public Map<String,Feed> getNamedFeeds() {
		return _namedFeeds;
	}

	public void setUnnamedFeedsUrls(Vector<URL> unnamedFeedsUrls) {
		this._unnamedFeedsUrls = unnamedFeedsUrls;
	}

	public Vector<URL> getUnnamedFeedsUrls() {
		return _unnamedFeedsUrls;
	}

	public void addNamedFeed(Feed newFeed, String feedName) {
		this._namedFeeds.put(feedName, newFeed);
	}

	public Vector<URL> getAllUrls() {
		Vector<URL> urls = new Vector<URL>();
		if(_unnamedFeedsUrls != null)
			urls.addAll(_unnamedFeedsUrls);
		if(_url != null)
			urls.add(_url);
		for (Feed f : _namedFeeds.values())
			urls.addAll(f.getAllUrls());
		return urls;
	}

	public void addUnnamedFeedURL(URL url) {
		_unnamedFeedsUrls.add(url);
	}

	public void deleteAll() {
		setNamedFeeds(new HashMap<String, Feed>());
		setUnnamedFeedsUrls(new Vector<URL>());
		setUrl(null);
	}

	public Vector<URL> getElementsUrls() {
		Vector<URL> urls = new Vector<URL>();
		if(_url != null)
			urls.add(_url);
		for (Feed f : _namedFeeds.values())
			urls.addAll(f.getAllUrls());
		return urls;
	}

	public void deleteElements() {
		setNamedFeeds(new HashMap<String, Feed>());
		setUrl(null);
	}
}
