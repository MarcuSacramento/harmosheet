// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.awtrenderers;

import harmotab.core.RepeatAttribute;
import harmotab.core.GlobalPreferences;
import harmotab.element.Bar;
import harmotab.renderer.LocationItem;
import harmotab.element.Element;
import java.awt.Graphics2D;
import harmotab.renderer.ElementRenderer;

public class AwtTabularBarRenderer extends ElementRenderer
{
    @Override
    public void paint(final Graphics2D g, final Element element, final LocationItem item) {
        final Bar bar = (Bar)element;
        final int BAR_HEIGHT = 32;
        final RepeatAttribute repeat = bar.getRepeatAttribute();
        final int x = item.getPointOfInterestX();
        final int y = item.getPointOfInterestY();
        g.drawLine(x, y, x, y + 32);
        if (repeat.isBeginning() && !item.getFlag(7)) {
            g.fillRect(x - 1, y, 3, 32);
            g.drawLine(x + 4, y, x + 4, y + 32);
            if (item.getFlag(9)) {
                g.fillOval(x + 8, y + 12, 3, 3);
                g.fillOval(x + 8, y + 20, 3, 3);
            }
        }
        if (repeat.isEnd() && !item.getFlag(8)) {
            g.fillRect(x - 1, y, 3, 32);
            g.drawLine(x - 5, y, x - 5, y + 32);
            if (repeat.getRepeatTimes() > 1) {
                g.fillOval(x - 12, y + 10, 4, 4);
                g.fillOval(x - 12, y + 18, 4, 4);
            }
        }
        if (repeat.getRepeatTimes() > 2 && !item.getFlag(8)) {
            g.drawString(String.valueOf(repeat.getRepeatTimes()) + "x", x - 14, y - 15);
        }
        if (repeat.isAlternateEnding()) {
            System.err.println("ElementRenderer:paintElement:Bar: Alternate ending not handled.");
        }
        if (GlobalPreferences.isBarNumbersDisplayed()) {
            g.setFont(AwtRenderersResources.m_barNumberFont);
            g.setColor(AwtRenderersResources.m_barNumberColor);
            g.drawString(new StringBuilder(String.valueOf(item.getExtra())).toString(), x - 5, y - 3);
        }
    }
}
