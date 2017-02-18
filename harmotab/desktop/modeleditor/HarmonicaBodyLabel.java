// 
// Decompiled by Procyon v0.5.30
// 

package harmotab.desktop.modeleditor;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.Color;
import harmotab.core.Localizer;
import javax.swing.Icon;
import harmotab.desktop.GuiIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.JLabel;

class HarmonicaBodyLabel extends JLabel implements MouseListener
{
    private static final long serialVersionUID = 1L;
    public static final int START = -1;
    public static final int END_DIATO = -2;
    public static final int END_CHROMA_NATURAL = -3;
    public static final int END_CHROMA_PUSHED = -4;
    private int m_hole;
    private ActionListener m_listener;
    
    public HarmonicaBodyLabel(final int hole) {
        this.m_hole = hole;
        this.m_listener = null;
        switch (this.m_hole) {
            case -1: {
                this.setIcon(GuiIcon.getIcon((byte)0));
                break;
            }
            case -2: {
                this.setIcon(GuiIcon.getIcon((byte)1));
                break;
            }
            case -3: {
                this.setIcon(GuiIcon.getIcon((byte)2));
                this.setText(String.valueOf(Localizer.get("ET_PISTON_RELEASED")) + "   ");
                break;
            }
            case -4: {
                this.setIcon(GuiIcon.getIcon((byte)3));
                this.setText(String.valueOf(Localizer.get("ET_PISTON_PUSHED")) + "   ");
                break;
            }
            default: {
                this.setIcon(GuiIcon.getIcon((byte)4));
                break;
            }
        }
        this.addMouseListener(this);
    }
    
    public void setActionListener(final ActionListener listener) {
        this.m_listener = listener;
        if (listener != null) {
            this.setBorder(new LineBorder(Color.GRAY));
            this.setCursor(new Cursor(12));
        }
        else {
            this.setBorder(null);
            this.setCursor(new Cursor(0));
        }
    }
    
    @Override
    public void mouseClicked(final MouseEvent event) {
        if (this.m_listener != null) {
            this.m_listener.actionPerformed(new ActionEvent(this, 0, ""));
        }
    }
    
    @Override
    public void mouseEntered(final MouseEvent event) {
        if (this.m_listener != null) {
            this.setBorder(new LineBorder(Color.BLACK));
        }
    }
    
    @Override
    public void mouseExited(final MouseEvent event) {
        if (this.m_listener != null) {
            this.setBorder(new LineBorder(Color.GRAY));
        }
    }
    
    @Override
    public void mousePressed(final MouseEvent event) {
    }
    
    @Override
    public void mouseReleased(final MouseEvent event) {
    }
}
