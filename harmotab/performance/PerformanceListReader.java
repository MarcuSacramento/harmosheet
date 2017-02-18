// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.performance;

import javax.xml.parsers.SAXParser;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

public class PerformanceListReader
{
    public void read(final PerformancesList list, final InputStream stream) throws IOException {
        try {
            final SAXParserFactory factory = SAXParserFactory.newInstance();
            final SAXParser parser = factory.newSAXParser();
            parser.parse(stream, new PerformanceListXMLHandler(list));
        }
        catch (ParserConfigurationException e) {
            throw new IOException(e.getMessage());
        }
        catch (SAXException e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
