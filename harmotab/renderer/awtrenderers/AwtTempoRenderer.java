// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.awtrenderers;

import java.awt.image.ImageObserver;
import harmotab.element.Tempo;
import harmotab.renderer.LocationItem;
import harmotab.element.Element;
import java.awt.Graphics2D;
import harmotab.renderer.ElementRenderer;

public class AwtTempoRenderer extends ElementRenderer
{
    @Override
    public void paint(final Graphics2D g, final Element element, final LocationItem item) {
        final Tempo tempo = (Tempo)element;
        g.drawImage(AwtRenderersResources.m_tempoImage, item.getX1(), item.getY1() + 3, null);
        g.drawString(" = " + tempo.getValue(), item.getX1() + 12, item.getY2() - 4);
    }
}
