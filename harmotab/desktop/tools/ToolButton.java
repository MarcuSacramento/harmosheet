// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import harmotab.desktop.actions.UserAction;
import javax.swing.Icon;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class ToolButton extends JButton implements MouseListener
{
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WIDTH = 32;
    private static final int DEFAULT_HEIGHT = 32;
    
    public ToolButton(final String toolTip, final String text) {
        this.setText(text);
        this.setToolTipText(toolTip);
        this.setFocusable(false);
        this.setBorderPainted(true);
        this.setContentAreaFilled(false);
        this.addMouseListener(this);
        final Dimension dim = new Dimension(32, 32);
        this.setPreferredSize(dim);
    }
    
    public ToolButton(final String toolTip, final byte icon, final String text) {
        this(toolTip, text);
        this.setIcon(ToolIcon.getIcon(icon));
        this.setWide(true);
    }
    
    public ToolButton(final String toolTip, final byte icon) {
        this(toolTip, "");
        this.setIcon(ToolIcon.getIcon(icon));
    }
    
    public ToolButton(final UserAction action) {
        this(action.getDescription(), action.getLabel());
        this.setIcon(action.getIcon());
        this.addActionListener(action);
    }
    
    public void setWide(final boolean wide) {
        if (wide) {
            this.setPreferredSize(null);
        }
        else {
            final Dimension dim = new Dimension(32, 32);
            this.setPreferredSize(dim);
        }
    }
    
    @Override
    public void mouseEntered(final MouseEvent e) {
        this.setContentAreaFilled(this.isEnabled());
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
        this.setContentAreaFilled(false);
    }
    
    @Override
    public void mousePressed(final MouseEvent e) {
    }
    
    @Override
    public void mouseClicked(final MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
    }
}
