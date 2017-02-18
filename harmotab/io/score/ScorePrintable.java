// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.io.score;

import java.awt.print.PrinterException;
import java.awt.image.ImageObserver;
import java.awt.Image;
import harmotab.core.ImagesListView;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import harmotab.core.Score;
import java.awt.print.Printable;

public class ScorePrintable implements Printable
{
    private static final float m_zoom = 1.5f;
    private Score m_score;
    private ArrayList<BufferedImage> m_images;
    
    public ScorePrintable(final Score score) {
        this.m_score = null;
        this.m_images = null;
        if (score == null) {
            throw new NullPointerException();
        }
        this.m_score = score;
    }
    
    @Override
    public int print(final Graphics g, final PageFormat pageFormat, final int pageIndex) throws PrinterException {
        final Graphics2D g2d = (Graphics2D)g;
        final int width = (int)pageFormat.getWidth();
        final int height = (int)pageFormat.getHeight();
        if (this.m_images == null) {
            final ImagesListView view = new ImagesListView(this.m_score, (int)(width * 1.5f), (int)(height * 1.5f));
            view.setPageNumberVisible(true);
            view.setFixedPageHeight(true);
            this.m_images = view.createImages();
        }
        if (pageIndex >= this.m_images.size()) {
            this.m_images = null;
            return 1;
        }
        final BufferedImage img = this.m_images.get(pageIndex);
        g2d.drawImage(img, 0, 0, width, height, null);
        return 0;
    }
}
