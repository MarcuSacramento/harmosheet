// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.awtrenderers;

import java.awt.image.ImageObserver;
import java.awt.Color;
import harmotab.element.Tab;
import harmotab.renderer.LocationItem;
import harmotab.element.Element;
import java.awt.Graphics2D;
import harmotab.core.GlobalPreferences;
import harmotab.renderer.ElementRenderer;

public class AwtArrowTabRenderer extends ElementRenderer
{
    private boolean m_blowUp;
    
    public AwtArrowTabRenderer() {
        this.m_blowUp = true;
        this.m_blowUp = (GlobalPreferences.getTabBlowDirection() == 1);
    }
    
    @Override
    public void paint(final Graphics2D g, final Element element, final LocationItem item) {
        final Tab tab = (Tab)element;
        final int x = item.getX1();
        final int y = item.getY2() - 30;
        final int direction = tab.getDirection();
        final int bend = tab.getBend();
        if (bend != 0) {
            if (bend == 2) {
                final Color color = g.getColor();
                g.setColor(Color.LIGHT_GRAY);
                g.fillOval(x, y - 20 + 5, 20, 20);
                g.setColor(color);
            }
            g.drawOval(x, y - 20 + 5, 20, 20);
        }
        final int hole = tab.getHole();
        if (hole != 0) {
            g.drawString(new StringBuilder(String.valueOf(hole)).toString(), x + ((hole > 9) ? 4 : 7), y);
        }
        if (direction != 0) {
            int yIndex = -1;
            if (this.m_blowUp) {
                yIndex = ((direction == 1) ? 1 : 0);
            }
            else {
                yIndex = ((direction != 1) ? 1 : 0);
            }
            final int dirY = y + 5;
            final int dirX = x + 2;
            g.drawImage(AwtRenderersResources.m_breathImage, dirX, dirY, dirX + 15, dirY + 20, 0, yIndex * 20, 15, (yIndex + 1) * 20, null);
        }
        final int effectX = x + ((hole < 10) ? 15 : 19);
        switch (tab.getEffect().getType()) {
            case 2: {
                g.drawLine(effectX, y - 3, effectX + 10, y - 7);
                break;
            }
            case 1: {
                g.drawLine(effectX + 0, y - 3, effectX + 2, y - 7);
                g.drawLine(effectX + 2, y - 7, effectX + 4, y - 3);
                g.drawLine(effectX + 4, y - 3, effectX + 6, y - 7);
                g.drawLine(effectX + 6, y - 7, effectX + 8, y - 3);
                g.drawLine(effectX + 8, y - 3, effectX + 10, y - 7);
                break;
            }
        }
        if (tab.isPushed()) {
            g.drawLine(x + 3, y - 12, x + 15, y - 12);
        }
    }
}
