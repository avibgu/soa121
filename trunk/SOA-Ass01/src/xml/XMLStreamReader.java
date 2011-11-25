package xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.ContentHandler;

public class XMLStreamReader {

	protected	Transformer		_transformer;
	protected	ContentHandler	_contentHandler;
	
	public XMLStreamReader() {

		try {
			setTransformer(TransformerFactory.newInstance().newTransformer());
		}
		catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setContentHandler(new RSSContentHandler());
	}
	
	public String readFromStream(URL url) throws Exception{

		//	Parsing from a stream to SAX callbacks

		if (null == url) throw new Exception("empty url");
        
        InputStream stream = null;
		
        try {
			stream = url.openStream();
		}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Source s = new StreamSource(stream);
		Result r = new SAXResult(getContentHandler());
		
		try {
			getTransformer().transform(s, r);	// calls SAX callbacks
		}
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return r.toString();
	}

	public void setTransformer(Transformer transformer) {
		this._transformer = transformer;
	}

	public Transformer getTransformer() {
		return _transformer;
	}

	public void setContentHandler(ContentHandler contentHandler) {
		this._contentHandler = contentHandler;
	}

	public ContentHandler getContentHandler() {
		return _contentHandler;
	}
}
