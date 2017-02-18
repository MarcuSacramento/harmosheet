// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.awtrenderers;

import harmotab.renderer.LocationItem;
import harmotab.element.Element;
import java.awt.Graphics2D;

public class AwtArrowBarRenderer extends AwtTabularBarRenderer
{
    @Override
    public void paint(final Graphics2D g, final Element element, final LocationItem item) {
        if (item.getParent() == null) {
            super.paint(g, element, item);
        }
    }
}
