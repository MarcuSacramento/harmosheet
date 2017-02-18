// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.awtrenderers;

import java.awt.image.ImageObserver;
import harmotab.core.Height;
import harmotab.element.KeySignature;
import harmotab.renderer.LocationItem;
import harmotab.element.Element;
import java.awt.Graphics2D;
import harmotab.renderer.ElementRenderer;

public class AwtKeySignatureRenderer extends ElementRenderer
{
    @Override
    public void paint(final Graphics2D g, final Element element, final LocationItem item) {
        final KeySignature ks = (KeySignature)element;
        int x = item.getX1();
        int y = item.getPointOfInterestY();
        final int refOrdinate = new Height((byte)4).getOrdinate();
        final int spacing = item.getExtra();
        final int shifting = refOrdinate * spacing - item.getPointOfInterestY();
        for (byte i = 1; i <= ks.getValue(); ++i) {
            y = KeySignature.getHeight(i).getOrdinate() * spacing - shifting - 9;
            g.drawImage(AwtRenderersResources.m_sharpImage, x + 5, y, null);
            x += 12;
        }
        for (byte i = -1; i >= ks.getValue(); --i) {
            y = KeySignature.getHeight(i).getOrdinate() * spacing - shifting - 15;
            g.drawImage(AwtRenderersResources.m_flatImage, x + 5, y, null);
            x += 12;
        }
    }
}
