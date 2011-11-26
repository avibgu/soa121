package feeds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

import exceptions.BadRequestException;
import exceptions.NotImplaementedException;

public class Feed {
	protected	String				_name;
	protected	URL					_url;
	
	public Feed(String name, URL url) {
	
		setName(name);
		setUrl(url);
	}

	public Feed(String name) {
		setName(name);
	}

	public void putNamedFeed(Vector<String> requestPath, String address) throws BadRequestException, NotImplaementedException{
		try {
			setUrl(new URL(address));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void setName(String name) {
		this._name = name;
	}

	public void setUrl(URL url) {
		this._url = url;
	}

	public URL getUrl() {
		return _url;
	}
	
	public String getName() {
		return _name;
	}
}
