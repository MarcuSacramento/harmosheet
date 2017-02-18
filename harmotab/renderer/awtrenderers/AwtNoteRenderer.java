// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.renderer.awtrenderers;

import java.awt.image.ImageObserver;
import harmotab.element.Note;
import harmotab.renderer.LocationItem;
import harmotab.element.Element;
import java.awt.Graphics2D;
import harmotab.renderer.ElementRenderer;

public class AwtNoteRenderer extends ElementRenderer
{
    public static final int REVERSE_QUEUE_ORDINATE = 85;
    
    @Override
    public void paint(final Graphics2D g, final Element element, final LocationItem item) {
        final Note note = (Note)element;
        final int NOTE_WIDTH = 20;
        final int NOTE_HEIGHT = 30;
        final int BLACK_BODY_INDEX = 6;
        final int x = item.getX1();
        int y = item.getPointOfInterestY() - 24;
        int xIndex = note.getFigure().getType() - 1;
        int yIndex = 0;
        int ordinate = note.getHeight().getOrdinate();
        final int spacing = item.getExtra();
        final int shifting = ordinate * spacing - item.getPointOfInterestY();
        final boolean isRest = note.isRest();
        if (isRest || item.getFlag(2)) {
            yIndex = 0;
        }
        else if (ordinate < 85 || item.getFlag(2)) {
            yIndex = 1;
            y += 18;
        }
        if (isRest) {
            yIndex = 2;
            ordinate = 85;
        }
        if (note.isHookable() && !note.isRest() && !item.getFlag(1)) {
            xIndex = 6;
        }
        final int EXTRA_LINE_WIDTH = 16;
        if (ordinate > 91) {
            int lineY = 92 * spacing - shifting;
            g.drawLine(x + 2, lineY, x + 16, lineY);
            if (ordinate > 93) {
                lineY = 94 * spacing - shifting;
                g.drawLine(x + 2, lineY, x + 16, lineY);
                if (ordinate > 95) {
                    lineY = 96 * spacing - shifting;
                    g.drawLine(x + 2, lineY, x + 16, lineY);
                    if (ordinate > 97) {
                        lineY = 98 * spacing - shifting;
                        g.drawLine(x + 2, lineY, x + 16, lineY);
                    }
                }
            }
        }
        if (ordinate < 81) {
            int lineY = 80 * spacing - shifting;
            g.drawLine(x, lineY, x + 16, lineY);
            if (ordinate < 79) {
                lineY = 78 * spacing - shifting;
                g.drawLine(x, lineY, x + 16, lineY);
                if (ordinate < 77) {
                    lineY = 76 * spacing - shifting;
                    g.drawLine(x, lineY, x + 16, lineY);
                    if (ordinate < 75) {
                        lineY = 74 * spacing - shifting;
                        g.drawLine(x, lineY, x + 16, lineY);
                        if (ordinate < 73) {
                            lineY = 72 * spacing - shifting;
                            g.drawLine(x, lineY, x + 16, lineY);
                        }
                    }
                }
            }
        }
        g.drawImage(AwtRenderersResources.m_notesImage, x, y, x + 20, y + 30, xIndex * 20, yIndex * 30, (xIndex + 1) * 20, (yIndex + 1) * 30, null);
        if (note.getFigure().isDotted()) {
            g.fillArc(x + 15, item.getPointOfInterestY() + 0, 4, 3, 0, 360);
        }
        if (item.getFlag(6)) {
            switch (note.getHeight().getAlteration()) {
                case 0: {
                    if (yIndex == 0) {
                        g.drawImage(AwtRenderersResources.m_naturalImage, x - 5, y + 15, null);
                        break;
                    }
                    if (yIndex == 1) {
                        g.drawImage(AwtRenderersResources.m_naturalImage, x - 5, y - 3, null);
                        break;
                    }
                    break;
                }
                case 1: {
                    if (yIndex == 0) {
                        g.drawImage(AwtRenderersResources.m_sharpImage, x - 5, y + 15, null);
                        break;
                    }
                    if (yIndex == 1) {
                        g.drawImage(AwtRenderersResources.m_sharpImage, x - 5, y - 3, null);
                        break;
                    }
                    break;
                }
                case 2: {
                    if (yIndex == 0) {
                        g.drawImage(AwtRenderersResources.m_flatImage, x - 5, y + 10, null);
                        break;
                    }
                    if (yIndex == 1) {
                        g.drawImage(AwtRenderersResources.m_flatImage, x - 5, y - 9, null);
                        break;
                    }
                    break;
                }
            }
        }
    }
}
