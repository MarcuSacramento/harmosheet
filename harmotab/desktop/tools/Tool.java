// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import java.awt.Component;
import harmotab.track.Track;
import java.awt.Graphics;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import harmotab.core.Score;
import java.awt.Container;
import harmotab.renderer.LocationItem;
import java.awt.Cursor;
import javax.swing.JPanel;

public abstract class Tool extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static Cursor DEFAULT_CURSOR;
    private static final int BORDER_SIZE = 5;
    private static final int GAP_SIZE = 0;
    protected LocationItem m_locationItem;
    protected Container m_container;
    protected Score m_score;
    
    static {
        Tool.DEFAULT_CURSOR = new Cursor(0);
    }
    
    public Tool(final Container container, final Score score, final LocationItem item) {
        this.m_locationItem = item;
        this.m_score = score;
        this.m_container = container;
        this.setLayout(new FlowLayout(0, 0, 0));
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setOpaque(false);
        this.setCursor(Tool.DEFAULT_CURSOR);
        this.setMinimumSize(new Dimension(-32768, 40));
        this.setMaximumSize(new Dimension(32767, 32767));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Tool.this.revalidate();
            }
        });
    }
    
    @Override
    public void paint(final Graphics g) {
        g.setColor(this.getBackground());
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 8, 8);
        super.paint(g);
    }
    
    public LocationItem getLocationItem() {
        return this.m_locationItem;
    }
    
    public Track getTrack() {
        final int trackId = this.m_locationItem.getTrackId();
        if (trackId == -1) {
            return null;
        }
        return this.m_score.getTrack(this.m_locationItem.getTrackId());
    }
    
    public Score getScore() {
        return this.m_score;
    }
    
    public void updateLocation() {
        int x = this.m_locationItem.getX1();
        int y = this.m_locationItem.m_y1 - 45;
        if (this.m_locationItem.m_y1 < 45) {
            y = this.m_locationItem.m_y2 + 5;
        }
        if (x + this.getWidth() > this.m_container.getWidth()) {
            x = this.m_container.getWidth() - this.getWidth() - 10;
        }
        this.setLocation(x, y);
    }
    
    @Override
    public void setVisible(final boolean visible) {
        super.setVisible(visible);
        if (visible) {
            int width = 0;
            int height = 0;
            Component[] components;
            for (int length = (components = this.getComponents()).length, i = 0; i < length; ++i) {
                final Component comp = components[i];
                final Dimension compSize = comp.getPreferredSize();
                width += compSize.width + 0;
                if (compSize.height > height) {
                    height = compSize.height;
                }
            }
            final Dimension size = new Dimension(width + 10, height + 10);
            this.setSize(size);
            this.setMinimumSize(size);
            this.setMaximumSize(size);
            this.setPreferredSize(size);
            Component[] components2;
            for (int length2 = (components2 = this.getComponents()).length, j = 0; j < length2; ++j) {
                final Component comp2 = components2[j];
                final Dimension compSize2 = comp2.getPreferredSize();
                compSize2.height = height;
                comp2.setPreferredSize(compSize2);
                comp2.setSize(compSize2);
                comp2.setMinimumSize(compSize2);
                comp2.setMaximumSize(compSize2);
            }
            this.m_container.add(this);
        }
        else {
            super.setVisible(false);
            this.m_container.remove(this);
        }
        if (visible) {
            this.updateLocation();
        }
    }
    
    public void addSeparator() {
        this.add(new JLabel(" "));
    }
    
    public abstract void keyTyped(final KeyEvent p0);
}
