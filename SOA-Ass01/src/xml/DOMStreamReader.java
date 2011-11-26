package xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

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

public class DOMStreamReader implements Runnable {

	protected	URL				_url;
	protected	OutputStream	_outputStream;
	protected	Transformer		_transformer;
	
	public DOMStreamReader(URL url, OutputStream outputStream) {

		setUrl(url);
		setOutputStream(outputStream);
		setTransformer(null);
		
		try {
			setTransformer(TransformerFactory.newInstance().newTransformer());
		}
		catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {

		Node node = readFeedContent();
		
		//TODO: filter(node);
		
		writeFeedContent(node);
	}

	protected Node readFeedContent() {

		InputStream inputStream = null;
		
		try {
			inputStream = getUrl().openStream();
		}
		catch (IOException e1) {
			e1.printStackTrace();
			
		}
		
		Source s = new StreamSource(inputStream);
		Result r = new DOMResult();
		
		try {
			getTransformer().transform(s, r);
		}
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ((DOMResult)r).getNode();
	}
	
	protected void writeFeedContent(Node node) {
		
		Source s2 = new DOMSource(node);
		Result r2 = new StreamResult(_outputStream);

		synchronized (_outputStream) {
			
			try {
				getTransformer().transform(s2, r2);
			}
			catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setUrl(URL url) {
		this._url = url;
	}

	public URL getUrl() {
		return _url;
	}

	public void setOutputStream(OutputStream outputStream) {
		this._outputStream = outputStream;
	}

	public OutputStream getOutputStream() {
		return _outputStream;
	}

	public void setTransformer(Transformer transformer) {
		this._transformer = transformer;
	}

	public Transformer getTransformer() {
		return _transformer;
	}
}
