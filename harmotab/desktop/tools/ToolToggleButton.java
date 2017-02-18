// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.tools;

import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import harmotab.desktop.actions.UserAction;
import java.awt.Dimension;
import javax.swing.Icon;
import java.awt.event.MouseListener;
import javax.swing.JToggleButton;

public class ToolToggleButton extends JToggleButton implements MouseListener
{
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_WIDTH = 32;
    private static final int DEFAULT_HEIGHT = 32;
    
    public ToolToggleButton(final String text, final byte icon) {
        this.setIcon(ToolIcon.getIcon(icon));
        this.setToolTipText(text);
        this.setPreferredSize(new Dimension(32, 32));
        this.setFocusable(false);
        this.setBorderPainted(true);
        this.setContentAreaFilled(false);
        this.addMouseListener(this);
    }
    
    public ToolToggleButton(final UserAction action) {
        this.setIcon(action.getIcon());
        this.setToolTipText(action.getDescription());
        this.setPreferredSize(new Dimension(32, 32));
        this.setFocusable(false);
        this.setBorderPainted(true);
        this.setContentAreaFilled(false);
        this.addMouseListener(this);
        this.addActionListener(action);
    }
    
    @Override
    public void setSelected(final boolean selected) {
        if (selected) {
            this.setContentAreaFilled(true);
        }
        super.setSelected(selected);
    }
    
    @Override
    public void mouseEntered(final MouseEvent e) {
        this.setContentAreaFilled(this.isEnabled());
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
        this.setContentAreaFilled(this.isEnabled() && this.isSelected());
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
