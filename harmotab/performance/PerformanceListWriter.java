// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.performance;

import javax.xml.transform.Transformer;
import java.util.Iterator;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.OutputStream;

public class PerformanceListWriter
{
    public void write(final OutputStream output, final PerformancesList performanceList) throws IOException {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.newDocument();
            final Element root = doc.createElement("harmotab");
            root.setAttribute("file-format-version", "3.1");
            root.setAttribute("file-type", "performance-list");
            root.setAttribute("harmotab-version", String.valueOf(3.1f));
            for (final Performance performance : performanceList) {
                final Element item = doc.createElement("performance");
                item.setAttribute("file", performance.getFile().getName());
                item.setAttribute("name", performance.getName());
                item.setAttribute("harmonica", performance.getHarmonica().getName());
                item.setAttribute("tunning", performance.getHarmonica().getTunning().getNoteName());
                root.appendChild(item);
            }
            doc.appendChild(root);
            final Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("encoding", "ISO-8859-1");
            transformer.transform(new DOMSource(doc), new StreamResult(output));
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        catch (TransformerException e2) {
            e2.printStackTrace();
            throw new IOException(e2);
        }
    }
}
