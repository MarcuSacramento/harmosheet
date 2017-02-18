// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop;

import java.awt.Color;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Graphics2D;
import harmotab.core.ScoreController;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.GraphicsConfiguration;
import harmotab.core.ScoreView;

public class ImageScoreView extends ScoreView
{
    static GraphicsConfiguration graphicsConfiguration;
    private Image m_scoreImage;
    
    static {
        ImageScoreView.graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    }
    
    public ImageScoreView(final ScoreController controller) {
        super(controller);
        this.m_scoreImage = null;
        this.m_scoreImage = null;
    }
    
    public Image getImage() {
        return this.m_scoreImage;
    }
    
    @Override
    protected void updateScoreView() {
        this.m_scoreImage = ImageScoreView.graphicsConfiguration.createCompatibleImage(this.m_viewWidth, this.m_viewHeight, 3);
        final Graphics2D g2d = (Graphics2D)this.m_scoreImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if (this.m_renderer != null) {
            this.m_renderer.paint(g2d, this.m_locations, new Point(0, 0));
        }
        else {
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(0, 0, this.m_viewWidth, this.m_viewHeight);
        }
        g2d.dispose();
    }
}
