// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.awtrenderers;

import harmotab.core.Localizer;
import java.awt.Color;
import harmotab.renderer.LocationItem;
import harmotab.element.Element;
import java.awt.Graphics2D;
import harmotab.core.GlobalPreferences;
import harmotab.renderer.ElementRenderer;

public class AwtTabularTabAreaRenderer extends ElementRenderer
{
    private boolean m_blowUp;
    
    public AwtTabularTabAreaRenderer() {
        this.m_blowUp = true;
        this.m_blowUp = (GlobalPreferences.getTabBlowDirection() == 1);
    }
    
    @Override
    public void paint(final Graphics2D g, final Element element, final LocationItem item) {
        final int y = item.m_y1 + item.m_height / 2;
        int x = 0;
        g.setColor(AwtRenderersResources.m_defaultForeground);
        g.setFont(AwtRenderersResources.m_defaultFont);
        g.drawLine(item.m_x1, y, item.getPointOfInterestX(), y);
        if (item.getLine() == 1) {
            g.setColor(Color.DARK_GRAY);
            x = item.m_x1 + 10;
            if (this.m_blowUp) {
                g.drawString(Localizer.get("S_BLOW"), x, y - 7);
                g.drawString(Localizer.get("S_DRAW"), x, item.m_y2 - 7);
            }
            else {
                g.drawString(Localizer.get("S_DRAW"), x, y - 7);
                g.drawString(Localizer.get("S_BLOW"), x, item.m_y2 - 7);
            }
        }
    }
}
