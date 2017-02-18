// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.awtrenderers;

import java.awt.Color;
import harmotab.element.Tab;
import harmotab.renderer.LocationItem;
import harmotab.element.Element;
import java.awt.Graphics2D;
import harmotab.core.GlobalPreferences;
import harmotab.renderer.ElementRenderer;

public class AwtTabularTabRenderer extends ElementRenderer
{
    private boolean m_blowUp;
    
    public AwtTabularTabRenderer() {
        this.m_blowUp = true;
        this.m_blowUp = (GlobalPreferences.getTabBlowDirection() == 1);
    }
    
    @Override
    public void paint(final Graphics2D g, final Element element, final LocationItem item) {
        final Tab tab = (Tab)element;
        final int BEND_WIDTH = 20;
        final int x = item.getX1();
        int y = item.getY1();
        if (this.m_blowUp) {
            switch (tab.getDirection()) {
                case 1: {
                    y += 15;
                    break;
                }
                case 0: {
                    y += 25;
                    break;
                }
                case 2: {
                    y += 38;
                    break;
                }
            }
        }
        else {
            switch (tab.getDirection()) {
                case 1: {
                    y += 38;
                    break;
                }
                case 0: {
                    y += 25;
                    break;
                }
                case 2: {
                    y += 15;
                    break;
                }
            }
        }
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
