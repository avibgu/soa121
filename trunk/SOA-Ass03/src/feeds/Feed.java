package feeds;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Feed {
	protected	URL					_url;
	protected	Map<String,Feed>	_namedFeeds;
	protected	Vector<URL>		_unnamedFeedsUrls;

	/**
	 * Constructor
	 */
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

	/**
	 * @return All URLs from this feed. Including _unnamedFeedsUrls (Collection)
	 * and other sub-feeds
	 */
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

	/**
	 * Delete all URLs
	 */
	public void deleteAll() {
		setNamedFeeds(new HashMap<String, Feed>());
		setUnnamedFeedsUrls(new Vector<URL>());
		setUrl(null);
	}

	/**
	 * 
	 * @return Vector<URL> which contain the main URL adress of this feed and all URL's of the sub-Feeds (No unnamed URL's included)
	 */
	public Vector<URL> getElementsUrls() {
		Vector<URL> urls = new Vector<URL>();
		if(_url != null)
			urls.add(_url);
		for (Feed f : _namedFeeds.values())
			urls.addAll(f.getAllUrls());
		return urls;
	}

	/**
	 * Delete only the main URL adress of this feed and the URLof it's sub-feeds. No URL's fron collection included
	 */
	public void deleteElements() {
		setNamedFeeds(new HashMap<String, Feed>());
		setUrl(null);
	}

	public Vector<Vector<String>> getFolderContent() {

		Vector<Vector<String>> content = new Vector<Vector<String>>();
		
		for (String key : _namedFeeds.keySet()){
			
			Vector<String> pair = new Vector<String>(2);
			
			if (_namedFeeds.get(key).getNamedFeeds().isEmpty()){

				pair.add("ELEMENT");
				pair.add(key);
				content.add(pair);
			}
			else{

				pair.add("SUBFOLDER");
				pair.add(key);
				content.add(pair);
			}
		}
		
		return content;
	}
}
