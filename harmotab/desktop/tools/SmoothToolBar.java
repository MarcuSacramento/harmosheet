// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.LayoutManager;
import java.awt.FlowLayout;
import java.awt.Container;
import javax.swing.JPanel;

public class SmoothToolBar extends JPanel
{
    private static final long serialVersionUID = 1L;
    private Container m_container;
    
    public void addSeparator() {
    }
    
    public SmoothToolBar(final Container container) {
        this.m_container = container;
        this.setOpaque(false);
        this.setLayout(new FlowLayout(3, 0, 0));
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.addSeparator();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SmoothToolBar.this.revalidate();
            }
        });
    }
    
    @Override
    public void setVisible(final boolean visible) {
        if (visible) {
            int width = 0;
            int height = 0;
            Component[] components;
            for (int length = (components = this.getComponents()).length, i = 0; i < length; ++i) {
                final Component comp = components[i];
                final Dimension compSize = comp.getPreferredSize();
                width += compSize.width;
                if (compSize.height > height) {
                    height = compSize.height;
                }
            }
            final Dimension size = new Dimension(width + 20, height);
            this.setSize(size);
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
    }
}
