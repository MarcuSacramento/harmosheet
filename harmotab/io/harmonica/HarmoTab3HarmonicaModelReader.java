// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.harmonica;

import harmotab.core.Height;
import harmotab.element.Tab;
import harmotab.harmonica.HarmonicaType;
import harmotab.throwables.FileFormatError;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import harmotab.harmonica.HarmonicaModel;

public class HarmoTab3HarmonicaModelReader extends HarmonicaModelReader
{
    public HarmoTab3HarmonicaModelReader(final HarmonicaModel model) {
        super(model);
    }
    
    @Override
    public void read(final File file) throws IOException {
        try {
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document doc = builder.parse(file);
            final NodeList childs = doc.getChildNodes();
            for (int i = 0; i < childs.getLength(); ++i) {
                final Node node = childs.item(i);
                if (node.getNodeName() == "harmotab") {
                    this.parseHarmoTab((Element)node);
                }
                else {
                    System.out.println("Root node different of 'harmotab' found !");
                }
            }
        }
        catch (ParserConfigurationException pce) {
            throw new IOException(pce);
        }
        catch (SAXException se) {
            throw new IOException(se);
        }
        catch (IOException ioe) {
            throw new IOException(ioe);
        }
    }
    
    private void parseHarmoTab(final Element harmotab) {
        if (harmotab.hasAttribute("file-type")) {
            if (!harmotab.getAttribute("file-type").equals("harmonica-model")) {
                throw new FileFormatError("Not harmo tab 3 model file !");
            }
        }
        else {
            System.err.println("File type not found !");
        }
        if (harmotab.hasAttribute("file-format-version")) {
            final float version = Float.parseFloat(harmotab.getAttribute("file-format-version"));
            if (version < 3.0) {
                throw new FileFormatError("Not harmo tab 3 model file !");
            }
            if (version > 3.0) {
                System.out.println("File version higher than 3.0 !");
            }
        }
        else {
            System.err.println("File type not found !");
        }
        final NodeList childs = harmotab.getChildNodes();
        for (int i = 0; i < childs.getLength(); ++i) {
            final Node node = childs.item(i);
            if (node.getNodeName().equals("harmonica")) {
                this.parseHarmonica((Element)node);
            }
            else if (node.getNodeName() != "#text") {
                System.out.println("Harmotab node different of 'harmonica' found ! (" + node.getNodeName() + ")");
            }
        }
    }
    
    private void parseHarmonica(final Element harmonica) {
        if (harmonica.hasAttribute("holes")) {
            final int holes = Integer.parseInt(harmonica.getAttribute("holes"));
            this.m_model.setNumberOfHoles(holes);
        }
        else {
            System.err.println("Attribute 'holes' not found !");
        }
        if (harmonica.hasAttribute("name")) {
            this.m_model.setName(harmonica.getAttribute("name"));
        }
        else {
            System.err.println("Attribute 'name' not found !");
        }
        if (harmonica.hasAttribute("type")) {
            this.m_model.setHarmonicaType(HarmonicaType.parseHarmonicaType(harmonica.getAttribute("type")));
        }
        final NodeList childs = harmonica.getChildNodes();
        for (int i = 0; i < childs.getLength(); ++i) {
            final Node node = childs.item(i);
            if (node.getNodeName().equals("hole")) {
                this.parseHole((Element)node);
            }
            else if (node.getNodeName() != "#text") {
                System.out.println("Harmonica node different of 'hole' found ! (" + node.getNodeName() + ")");
            }
        }
    }
    
    private void parseHole(final Element hole) {
        if (hole.hasAttribute("number")) {
            final int holeNumber = Integer.parseInt(hole.getAttribute("number"));
            final NodeList childs = hole.getChildNodes();
            for (int i = 0; i < childs.getLength(); ++i) {
                final Node node = childs.item(i);
                if (node.getNodeName().equals("alt")) {
                    this.parseAlt((Element)node, holeNumber);
                }
                else if (node.getNodeName() != "#text") {
                    System.out.println("Harmonica node different of 'alt' found ! (" + node.getNodeName() + ")");
                }
            }
            return;
        }
        throw new FileFormatError("Attribute 'number' not found !");
    }
    
    private void parseAlt(final Element alt, final int hole) {
        if (alt.hasAttribute("type")) {
            final String type = alt.getAttribute("type");
            final String value = alt.getTextContent();
            final Tab tab = new Tab(hole);
            tab.setBreath(type);
            tab.setPushed(alt.hasAttribute("piston") && alt.getAttribute("piston").equals("pushed"));
            this.m_model.setHeight(tab, new Height(value));
            return;
        }
        throw new FileFormatError("Attribute 'type' not found !");
    }
}
