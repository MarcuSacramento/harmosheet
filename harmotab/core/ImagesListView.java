// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.core;

import java.awt.Color;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import harmotab.renderer.LocationList;
import harmotab.renderer.ElementRendererBundle;
import harmotab.renderer.AwtPrintingElementRendererBundle;
import harmotab.renderer.ScoreRenderer;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;

public class ImagesListView
{
    private static GraphicsConfiguration graphicsConfiguration;
    private Score m_score;
    private int m_width;
    private int m_height;
    private boolean m_pageNumberVisible;
    private boolean m_fixedPageHeight;
    
    static {
        ImagesListView.graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }
    
    public ImagesListView(final Score score, final int width, final int height) {
        this.m_score = score;
        this.m_width = width;
        this.m_height = height;
        this.m_pageNumberVisible = false;
        this.m_fixedPageHeight = false;
    }
    
    public void setPageNumberVisible(final boolean visible) {
        this.m_pageNumberVisible = visible;
    }
    
    public boolean getPageNumberVisible() {
        return this.m_pageNumberVisible;
    }
    
    public void setFixedPageHeight(final boolean fixed) {
        this.m_fixedPageHeight = fixed;
    }
    
    public boolean getFixedPageHeight() {
        return this.m_fixedPageHeight;
    }
    
    public ArrayList<BufferedImage> createImages() {
        final ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        final ScoreRenderer renderer = new ScoreRenderer(this.m_score);
        renderer.setElementRenderer(new AwtPrintingElementRendererBundle());
        int width = this.m_width;
        int height = this.m_height;
        renderer.setPageSize(width, height);
        renderer.setMultiline(true);
        final LocationList locations = new LocationList();
        renderer.layout(locations);
        if (width == Integer.MAX_VALUE) {
            renderer.setHeaderDrawingEnabled(false);
            width = locations.getRightOrdinate() + 150;
            height = locations.getBottomOrdinate();
            renderer.setPageSize(width, height);
            locations.reset();
            renderer.layout(locations);
        }
        if (height == Integer.MAX_VALUE) {
            height = locations.getBottomOrdinate() + renderer.getInterlineHeight();
        }
        for (int lineHeight = renderer.getLineHeight(), totalHeight = locations.getBottomOrdinate(), pageOffset = 0, localY = renderer.getHeaderHeight() + renderer.getInterlineHeight(), page = 1; pageOffset < totalHeight && totalHeight - pageOffset > lineHeight; pageOffset += localY, localY = 0, ++page) {
            while (localY <= height) {
                localY += lineHeight;
            }
            localY -= lineHeight;
            final BufferedImage img = this.createBlankImage(width, this.m_fixedPageHeight ? this.m_height : localY);
            final Graphics2D g2d = (Graphics2D)img.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
            renderer.setPageSize(width, localY);
            renderer.paint(g2d, locations, new Point(0, 0));
            if (this.m_pageNumberVisible) {
                final String pageString = "- " + page + " -";
                g2d.drawString(pageString, width / 2 - g2d.getFontMetrics().stringWidth(pageString) / 2, this.m_height - 15);
            }
            images.add(img);
            locations.addVerticalScrolling(localY);
        }
        return images;
    }
    
    private BufferedImage createBlankImage(final int width, final int height) {
        final BufferedImage image = ImagesListView.graphicsConfiguration.createCompatibleImage(width, height);
        final Graphics2D g2d = (Graphics2D)image.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        return image;
    }
}
