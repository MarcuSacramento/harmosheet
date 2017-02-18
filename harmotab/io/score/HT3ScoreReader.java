// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.score;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import java.io.IOException;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import harmotab.throwables.FileFormatException;
import harmotab.io.SerializedObject;
import harmotab.io.ObjectSerializer;
import harmotab.io.XmlSerializedObject;
import harmotab.io.XmlObjectSerializer;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import harmotab.core.Score;

public class HT3ScoreReader extends ScoreReader
{
    public HT3ScoreReader(final Score score, final String path) {
        super(score, path);
    }
    
    @Override
    protected void read(final Score score, final File file) throws IOException, FileFormatException {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.parse(file);
            final NodeList childs = doc.getChildNodes();
            for (int i = 0; i < childs.getLength(); ++i) {
                final Node node = childs.item(i);
                if (node.getNodeName().equals("harmotab")) {
                    final NodeList childs2 = node.getChildNodes();
                    for (int j = 0; j < childs2.getLength(); ++j) {
                        final Node node2 = node.getChildNodes().item(j);
                        if (node2.getNodeName().equals("score")) {
                            final XmlObjectSerializer serializer = new XmlObjectSerializer(doc);
                            score.deserialize(serializer, new XmlSerializedObject(serializer, node2));
                        }
                    }
                }
                else {
                    System.out.println("Root node different of 'harmotab' found !");
                }
            }
        }
        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
            throw new FileFormatException("Invalid score file format.", pce);
        }
        catch (SAXException se) {
            se.printStackTrace();
            throw new FileFormatException("Invalid score file format.", se);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            throw new IOException(ioe);
        }
    }
}
