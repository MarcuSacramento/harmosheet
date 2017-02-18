// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.performance;

import org.xml.sax.SAXException;
import java.io.File;
import harmotab.core.Height;
import harmotab.harmonica.Harmonica;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class PerformanceListXMLHandler extends DefaultHandler
{
    private PerformancesList mList;
    
    public PerformanceListXMLHandler(final PerformancesList list) {
        this.mList = null;
        this.mList = list;
    }
    
    @Override
    public void startElement(final String uri, final String localName, final String name, final Attributes attributes) throws SAXException {
        name.equalsIgnoreCase("harmotab");
        if (name.equalsIgnoreCase("performance")) {
            String perfFile = "";
            String perfName = "";
            final Harmonica perfHarmonica = new Harmonica();
            for (int i = 0; i < attributes.getLength(); ++i) {
                final String attr = attributes.getLocalName(i);
                final String value = attributes.getValue(i);
                if (attr.equals("file")) {
                    perfFile = value;
                }
                else if (attr.equals("name")) {
                    perfName = value;
                }
                else if (attr.equals("harmonica")) {
                    perfHarmonica.setName(value);
                }
                else if (attr.equals("tunning")) {
                    perfHarmonica.setTunning(new Height(value));
                }
            }
            final Performance perf = new Performance(new File(perfFile), perfName, perfHarmonica);
            this.mList.add(perf);
        }
    }
}
