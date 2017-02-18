// 
// Decompiled by Procyon v0.5.30
// 

package rvt.util.gui;

import java.awt.Insets;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Container;
import java.awt.LayoutManager;

public class VerticalLayout implements LayoutManager
{
    public static final int CENTER = 0;
    public static final int RIGHT = 1;
    public static final int LEFT = 2;
    public static final int BOTH = 3;
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    private int vgap;
    private int alignment;
    private int anchor;
    
    public VerticalLayout() {
        this(5, 0, 1);
    }
    
    public VerticalLayout(final int vgap) {
        this(vgap, 0, 1);
    }
    
    public VerticalLayout(final int vgap, final int alignment) {
        this(vgap, alignment, 1);
    }
    
    public VerticalLayout(final int vgap, final int alignment, final int anchor) {
        this.vgap = vgap;
        this.alignment = alignment;
        this.anchor = anchor;
    }
    
    private Dimension layoutSize(final Container parent, final boolean minimum) {
        final Dimension dim = new Dimension(0, 0);
        synchronized (parent.getTreeLock()) {
            for (int n = parent.getComponentCount(), i = 0; i < n; ++i) {
                final Component c = parent.getComponent(i);
                if (c.isVisible()) {
                    final Dimension d = minimum ? c.getMinimumSize() : c.getPreferredSize();
                    dim.width = Math.max(dim.width, d.width);
                    final Dimension dimension = dim;
                    dimension.height += d.height;
                    if (i > 0) {
                        final Dimension dimension2 = dim;
                        dimension2.height += this.vgap;
                    }
                }
            }
        }
        // monitorexit(parent.getTreeLock())
        final Insets insets = parent.getInsets();
        final Dimension dimension3 = dim;
        dimension3.width += insets.left + insets.right;
        final Dimension dimension4 = dim;
        dimension4.height += insets.top + insets.bottom + this.vgap + this.vgap;
        return dim;
    }
    
    @Override
    public void layoutContainer(final Container parent) {
        final Insets insets = parent.getInsets();
        synchronized (parent.getTreeLock()) {
            final int n = parent.getComponentCount();
            final Dimension pd = parent.getSize();
            int y = 0;
            for (int i = 0; i < n; ++i) {
                final Component c = parent.getComponent(i);
                final Dimension d = c.getPreferredSize();
                y += d.height + this.vgap;
            }
            y -= this.vgap;
            if (this.anchor == 1) {
                y = insets.top;
            }
            else if (this.anchor == 0) {
                y = (pd.height - y) / 2;
            }
            else {
                y = pd.height - y - insets.bottom;
            }
            for (int i = 0; i < n; ++i) {
                final Component c = parent.getComponent(i);
                final Dimension d = c.getPreferredSize();
                int x = insets.left;
                int wid = d.width;
                if (this.alignment == 0) {
                    x = (pd.width - d.width) / 2;
                }
                else if (this.alignment == 1) {
                    x = pd.width - d.width - insets.right;
                }
                else if (this.alignment == 3) {
                    wid = pd.width - insets.left - insets.right;
                }
                c.setBounds(x, y, wid, d.height);
                y += d.height + this.vgap;
            }
        }
        // monitorexit(parent.getTreeLock())
    }
    
    @Override
    public Dimension minimumLayoutSize(final Container parent) {
        return this.layoutSize(parent, false);
    }
    
    @Override
    public Dimension preferredLayoutSize(final Container parent) {
        return this.layoutSize(parent, false);
    }
    
    @Override
    public void addLayoutComponent(final String name, final Component comp) {
    }
    
    @Override
    public void removeLayoutComponent(final Component comp) {
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.getClass().getName()) + "[vgap=" + this.vgap + " align=" + this.alignment + " anchor=" + this.anchor + "]";
    }
}
