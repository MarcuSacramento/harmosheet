// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.awtrenderers;

import java.awt.image.ImageObserver;
import harmotab.core.Localizer;
import java.awt.Color;
import harmotab.renderer.LocationItem;
import harmotab.element.Element;
import java.awt.Graphics2D;
import harmotab.core.GlobalPreferences;
import harmotab.renderer.ElementRenderer;

public class AwtArrowTabAreaRenderer extends ElementRenderer
{
    private boolean m_blowUp;
    
    public AwtArrowTabAreaRenderer() {
        this.m_blowUp = true;
        this.m_blowUp = (GlobalPreferences.getTabBlowDirection() == 1);
    }
    
    @Override
    public void paint(final Graphics2D g, final Element element, final LocationItem item) {
        final int y = item.m_y1 + item.m_height / 2;
        final int x = item.m_x1 - 10;
        int dirY = -1;
        final int dirX = x + 8;
        int yIndex = -1;
        String str = null;
        if (item.getLine() == 1) {
            g.setColor(Color.DARK_GRAY);
            yIndex = 1;
            str = (this.m_blowUp ? Localizer.get("S_BLOW") : Localizer.get("S_DRAW"));
            dirY = y - 20;
            g.drawImage(AwtRenderersResources.m_breathImage, dirX, dirY, dirX + 15, dirY + 20, 0, yIndex * 20, 15, (yIndex + 1) * 20, null);
            g.drawString(str, x, y - 7);
            yIndex = 0;
            str = (this.m_blowUp ? Localizer.get("S_DRAW") : Localizer.get("S_BLOW"));
            dirY = y + 3;
            g.drawImage(AwtRenderersResources.m_breathImage, dirX, dirY, dirX + 15, dirY + 20, 0, yIndex * 20, 15, (yIndex + 1) * 20, null);
            g.drawString(str, x, item.m_y2 - 7);
        }
    }
}
