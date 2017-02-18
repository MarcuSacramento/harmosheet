// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.components;

import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

public class CountdownPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 80;
    private static final int HEIGHT = 80;
    protected String m_label;
    protected int m_value;
    protected float m_fraction;
    private final Font NUMBER_FONT;
    private final Color COLOR1;
    private final Color COLOR2;
    
    public CountdownPanel() {
        this.m_label = "";
        this.m_value = 0;
        this.m_fraction = 0.0f;
        this.NUMBER_FONT = new Font("Sans-serif", 0, 96);
        this.COLOR1 = Color.GRAY;
        this.COLOR2 = Color.WHITE;
        this.setPreferredSize(new Dimension(80, 80));
        this.setBackground(Color.WHITE);
    }
    
    public void setValue(final String label, final int value, final float fraction) {
        this.m_label = label;
        this.m_value = value;
        this.m_fraction = fraction;
        this.repaint();
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final boolean even = false;
        final int angle = (int)((even ? -1 : 0) - this.m_fraction * 360.0f);
        final int width = this.getWidth();
        final int height = this.getHeight();
        g2d.setColor(even ? this.COLOR1 : this.COLOR2);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        g2d.setColor(even ? this.COLOR2 : this.COLOR1);
        g2d.fillArc(-width / 2, -height / 2, width * 2, height * 2, 90, angle);
        float fontSize = ((width < height) ? width : height) - 30.0f;
        fontSize = Math.max(fontSize, 48.0f);
        g2d.setFont(this.NUMBER_FONT.deriveFont(fontSize));
        g2d.setColor(Color.BLACK);
        final int x = width / 2 - g2d.getFontMetrics().stringWidth(this.m_label) / 2;
        final int y = height / 2 + g2d.getFontMetrics().getHeight() / 4;
        g2d.drawString(this.m_label, x, y);
    }
}
