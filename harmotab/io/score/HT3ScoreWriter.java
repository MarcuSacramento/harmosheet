// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.score;

import javax.xml.transform.Transformer;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Node;
import harmotab.io.ObjectSerializer;
import harmotab.io.XmlSerializedObject;
import harmotab.io.XmlObjectSerializer;
import harmotab.HarmoTabConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import harmotab.core.Score;

public class HT3ScoreWriter extends ScoreWriter
{
    public HT3ScoreWriter(final Score score, final String path) {
        super(score, path);
    }
    
    @Override
    protected void write(final Score score, final File file) throws IOException {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.newDocument();
            final Element format = doc.createElement("harmotab");
            format.setAttribute("file-format-version", "3.0");
            format.setAttribute("file-type", "score");
            format.setAttribute("harmotab-version", HarmoTabConstants.getVersionString());
            final XmlObjectSerializer xmlSerializer = new XmlObjectSerializer(doc);
            final XmlSerializedObject serializedScore = (XmlSerializedObject)score.serialize(xmlSerializer);
            format.appendChild(serializedScore.getXmlNode());
            doc.appendChild(format);
            final Source source = new DOMSource(doc);
            final Result resultat = new StreamResult(file);
            final TransformerFactory fabrique = TransformerFactory.newInstance();
            final Transformer transformer = fabrique.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("encoding", "ISO-8859-1");
            transformer.transform(source, resultat);
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
