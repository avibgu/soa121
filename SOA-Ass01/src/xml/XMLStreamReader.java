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

	Transformer _transformer;
	
	public XMLStreamReader() {

		try {
			_transformer = TransformerFactory.newInstance().newTransformer();
		}
		catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readFromStream(URL url) throws Exception{

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
		ContentHandler h = new RSSContentHandler();
		Result r = new SAXResult(h);
		
		try {
			_transformer.transform(s, r);	// calls SAX callbacks
		}
		catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
