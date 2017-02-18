// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.harmonica;

import javax.xml.transform.Transformer;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import harmotab.core.Height;
import harmotab.element.Tab;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilderFactory;
import harmotab.harmonica.HarmonicaModel;
import java.io.File;

public class HarmoTab3HarmonicaModelWriter extends HarmonicaModelWriter
{
    public HarmoTab3HarmonicaModelWriter(final File file) {
        super(file);
    }
    
    @Override
    public void writeFile(final HarmonicaModel model) throws IOException {
        try {
            final int numberOfHoles = model.getNumberOfHoles();
            final boolean hasPiston = model.getHarmonicaType().hasPiston();
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.newDocument();
            final Element format = doc.createElement("harmotab");
            format.setAttribute("file-format-version", "3.0");
            format.setAttribute("file-type", "harmonica-model");
            format.setAttribute("harmotab-version", "3.0");
            final Element harmonica = doc.createElement("harmonica");
            harmonica.setAttribute("name", model.getName());
            harmonica.setAttribute("holes", new StringBuilder(String.valueOf(model.getNumberOfHoles())).toString());
            harmonica.setAttribute("type", model.getHarmonicaType().toString());
            for (int i = 1; i <= numberOfHoles; ++i) {
                final Element hole = doc.createElement("hole");
                hole.setAttribute("number", String.valueOf(i));
                for (byte j = 0; j < 6; ++j) {
                    Tab tab = HarmonicaModel.createTab(i, j, false);
                    if (model.isSet(tab)) {
                        final Element alt = doc.createElement("alt");
                        alt.setAttribute("type", tab.getBreathName());
                        if (hasPiston) {
                            alt.setAttribute("piston", "released");
                        }
                        final Height height = model.getHeight(tab);
                        alt.setTextContent(String.valueOf(height.getNoteName()) + height.getOctave());
                        hole.appendChild(alt);
                    }
                    if (hasPiston) {
                        tab = HarmonicaModel.createTab(i, j, true);
                        if (model.isSet(tab)) {
                            final Element alt = doc.createElement("alt");
                            alt.setAttribute("type", tab.getBreathName());
                            if (hasPiston) {
                                alt.setAttribute("piston", "pushed");
                            }
                            final Height height = model.getHeight(tab);
                            alt.setTextContent(String.valueOf(height.getNoteName()) + height.getOctave());
                            hole.appendChild(alt);
                        }
                    }
                }
                harmonica.appendChild(hole);
            }
            format.appendChild(harmonica);
            doc.appendChild(format);
            final Source source = new DOMSource(doc);
            final Result resultat = new StreamResult(this.m_file);
            final TransformerFactory fabrique = TransformerFactory.newInstance();
            final Transformer transformer = fabrique.newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("encoding", "ISO-8859-1");
            transformer.transform(source, resultat);
        }
        catch (ParserConfigurationException e) {
            throw new IOException(e);
        }
        catch (TransformerConfigurationException e2) {
            throw new IOException(e2);
        }
        catch (TransformerException e3) {
            throw new IOException(e3);
        }
    }
}
